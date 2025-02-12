/*
package flight_booking.demo.security.common.filter;

import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.jwt.TokenProvider;
import flight_booking.demo.security.oauth.OAuth2SuccessHandler;
import flight_booking.demo.security.token.entity.RefreshToken;
import flight_booking.demo.security.token.repository.RefreshTokenRepository;
import flight_booking.demo.security.utils.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class RefreshAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String accessToken = extractToken(authorizationHeader);

        if (accessToken != null) {
            if (tokenProvider.validToken(accessToken)) {
                System.out.println("이건 실행이 왜 되는거지?");
                // 액세스 토큰이 유효하면 SecurityContext에 저장
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // 액세스 토큰이 만료된 경우
                handleExpiredToken(request, response);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void handleExpiredToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = CookieUtil.getCookie(request, "refresh_token");

        if (refreshToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Refresh token is missing.");
            return;
        }

        RefreshToken storedRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);
        if (storedRefreshToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid refresh token.");
            return;
        }

        // 유저 찾기
        String userId = storedRefreshToken.getUserId();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not found.");
            return;
        }

        // 새 액세스 토큰 발급
        String newAccessToken = tokenProvider.generateToken(user, OAuth2SuccessHandler.ACCESS_TOKEN_DURATION);

        // 응답 헤더에 새 액세스 토큰 추가
        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.getWriter().write("Access token refreshed.");

        // SecurityContext 업데이트
        Authentication authentication = tokenProvider.getAuthentication(newAccessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}*/
