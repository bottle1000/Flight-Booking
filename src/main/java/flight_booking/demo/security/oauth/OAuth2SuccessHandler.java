package flight_booking.demo.security.oauth;

import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.jwt.TokenProvider;
import flight_booking.demo.security.token.entity.RefreshToken;
import flight_booking.demo.security.token.repository.RefreshTokenRepository;
import flight_booking.demo.security.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
  public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
  public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
  public static final String REDIRECT_PATH = "/";


  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
  private final UserRepository userRepository;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

    // 프로필 사진 쿠키에 추가
    addProfilePictureUrlCookie(response, oAuth2User);

    // OAuth2에서 가져온 이메일을 통해 사용자 정보 조회
    User user = userRepository.findByEmail((String) oAuth2User.getAttributes().get("email"))
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + (String) oAuth2User.getAttributes().get("email")));

    // 1. 리프레시 토큰 생성
    String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);

    // 2. 리프레시 토큰을 데이터베이스에 저장 또는 업데이트
    saveRefreshToken(user.getId(), refreshToken);

    // 3. 리프레시 토큰을 쿠키에 저장
    addRefreshTokenToCookie(request, response, refreshToken);

    // 4. 액세스 토큰 생성
    String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
    System.out.println(" 생성된 AccessToken: " + accessToken);
    System.out.println(" 생성된 RefreshToken: " + refreshToken);
    // 5. 액세스 토큰을 쿠키에 저장
    CookieUtil.addCookie(response, "access_token", accessToken, (int) ACCESS_TOKEN_DURATION.toSeconds());

    // 6. 인증 관련 설정값, 쿠키 제거
    clearAuthenticationAttributes(request, response);

    // 7. URL에 토큰을 포함하지 않고 리디렉트 (토큰은 쿠키에 포함되어 전송됨)
    getRedirectStrategy().sendRedirect(request, response, REDIRECT_PATH);
  }


  //생성된 리프레시 토큰을 전달받아 데이터 베이스에 저장
  private void saveRefreshToken(String userId, String newRefreshToken) {
    RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId);
        if(refreshToken != null){
          refreshToken.update(newRefreshToken);
          refreshTokenRepository.save(refreshToken);
        }else{
           refreshToken = new RefreshToken(userId, newRefreshToken);
          System.out.println("onAuthenticationSuccess User: " + userId);
          System.out.println("onAuthenticationSuccess refreshToken: " + refreshToken.getRefreshToken());
          refreshTokenRepository.save(refreshToken);
        }
  }

  // 인증 관련 설정값, 쿠키 제거
  private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
    int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
    CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
    CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
  }

  private void addProfilePictureUrlCookie(HttpServletResponse response, OAuth2User oAuth2User){
    String profilePictureUrl = (String) oAuth2User.getAttribute("picture");
    Cookie profileCookie = new Cookie("profilePictureUrl", profilePictureUrl);
    profileCookie.setPath("/");
    response.addCookie(profileCookie);
  }


  // 인증 관련 설정값, 쿠키 제거
  private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
    super.clearAuthenticationAttributes(request);
    authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
  }


  private String getTargetUrl(String token) {
    return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
        .queryParam("token", token)
        .build()
        .toUriString();
  }
}