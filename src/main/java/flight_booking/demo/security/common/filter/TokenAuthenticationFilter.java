package flight_booking.demo.security.common.filter;

import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.jwt.TokenProvider;
import flight_booking.demo.security.oauth.OAuth2SuccessHandler;
import flight_booking.demo.security.token.entity.RefreshToken;
import flight_booking.demo.security.token.repository.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("TokenAuthenticationFilter 실행됨: " + request.getRequestURI());

        // 1️ access_token 가져오기
        String accessToken = tokenProvider.getAccessToken(request);
        System.out.println("가져온 accessToken: " + accessToken);

        if (accessToken != null && tokenProvider.validToken(accessToken)) {
            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            System.out.println(" 유효하지 않은 accessToken, refreshToken 검토 중...");

            // 2️ refresh_token 가져오기
            String refreshToken = tokenProvider.getRefreshToken(request);
            System.out.println(" 가져온 refreshToken: " + refreshToken);

            if (refreshToken != null) {
                RefreshToken storedRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);

                if (storedRefreshToken != null) {
                    System.out.println(" 유효한 refreshToken 확인됨!");

                    // 3 유저 정보 가져와서 새로운 access_token 발급
                    User user = userRepository.findById(storedRefreshToken.getUserId()).orElse(null);

                    // 블랙리스트 체크
                    if(user.getIsBlacklisted()){
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "당신은 블랙되었습니다");
                        return;
                    }
                    if (user != null) {
                        String newAccessToken = tokenProvider.generateToken(user, OAuth2SuccessHandler.ACCESS_TOKEN_DURATION);
                        System.out.println(" 새로운 accessToken 발급됨!");

                        // 4️ 새로운 access_token을 쿠키에 저장
                        Cookie accessTokenCookie = new Cookie("access_token", newAccessToken);
                        accessTokenCookie.setPath("/");
                        accessTokenCookie.setHttpOnly(true);
                        accessTokenCookie.setMaxAge((int) OAuth2SuccessHandler.ACCESS_TOKEN_DURATION.toSeconds());
                        response.addCookie(accessTokenCookie);

                        // 5️ 새로운 access_token으로 인증 설정
                        Authentication authentication = tokenProvider.getAuthentication(newAccessToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else {
                    System.out.println("refreshToken이 없거나 유효하지 않음.");
                }
            }
        }

        filterChain.doFilter(request, response);
    }




}
