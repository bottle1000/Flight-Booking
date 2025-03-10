package flight_booking.demo.security.common.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

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

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		// /actuator/prometheus 경로는 필터 처리 없이 바로 통과
		if (request.getRequestURI().startsWith("/actuator/prometheus") || request.getRequestURI().startsWith("/"))
		{ filterChain.doFilter(request, response); return; }


		// 1️ access_token 가져오기
		String accessToken = tokenProvider.getAccessToken(request);

		if (accessToken != null && tokenProvider.validToken(accessToken)) {
			Authentication authentication = tokenProvider.getAuthentication(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {

			// 2️ refresh_token 가져오기
			String refreshToken = tokenProvider.getRefreshToken(request);

			if (refreshToken != null) {
				RefreshToken storedRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);

				if (storedRefreshToken != null) {

					// 3 유저 정보 가져와서 새로운 access_token 발급
					User user = userRepository.findById(storedRefreshToken.getUserId()).orElse(null);

					if (user != null) {
						String newAccessToken = tokenProvider.generateToken(user,
							OAuth2SuccessHandler.ACCESS_TOKEN_DURATION);

						// 4️ 새로운 access_token을 쿠키에 저장
						Cookie accessTokenCookie = new Cookie("access_token", newAccessToken);
						accessTokenCookie.setPath("/");
						accessTokenCookie.setHttpOnly(true);
						accessTokenCookie.setMaxAge((int)OAuth2SuccessHandler.ACCESS_TOKEN_DURATION.toSeconds());
						response.addCookie(accessTokenCookie);
						// 5️ 새로운 access_token으로 인증 설정
						Authentication authentication = tokenProvider.getAuthentication(newAccessToken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
		}

		filterChain.doFilter(request, response);
	}

}
