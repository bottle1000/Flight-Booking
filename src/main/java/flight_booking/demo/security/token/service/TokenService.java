package flight_booking.demo.security.token.service;

import org.springframework.stereotype.Service;

import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.service.UserService;
import flight_booking.demo.security.jwt.TokenProvider;
import flight_booking.demo.security.oauth.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {
	private final TokenProvider tokenProvider;
	private final RefreshTokenService refreshTokenService;
	private final UserService userService;

	public String createNewAccessToken(String refreshToken) {
		//토큰 유효성 검사에 실패하면 예외 발생
		if (!tokenProvider.validToken(refreshToken)) {
			throw new IllegalArgumentException("Unexpected token");
		}

		String userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId(); //리프레시 토큰을 조회하여 UserID 가져옴
		User user = userService.findById(userId); // UserID로 조회하고 객체 생성
		return tokenProvider.generateToken(user, OAuth2SuccessHandler.ACCESS_TOKEN_DURATION);
	}
}
