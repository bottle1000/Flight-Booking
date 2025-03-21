package flight_booking.demo.security.token.service;

import org.springframework.stereotype.Service;

import flight_booking.demo.security.token.entity.RefreshToken;
import flight_booking.demo.security.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken findByRefreshToken(String refreshToken) {

		RefreshToken reFreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);

		if (reFreshToken == null) {
			throw new IllegalArgumentException("User not found with refreshToken: " + refreshToken);
		}
		return reFreshToken;
	}
}
