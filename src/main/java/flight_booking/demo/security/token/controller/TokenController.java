package flight_booking.demo.security.token.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import flight_booking.demo.security.token.dto.CreateAccessTokenRequest;
import flight_booking.demo.security.token.dto.CreateAccessTokenResponse;
import flight_booking.demo.security.token.service.TokenService;
import lombok.RequiredArgsConstructor;

public class TokenController {
	@RequiredArgsConstructor
	@RestController
	public class TokenApiController {
		private final TokenService tokenService;

		@PostMapping("/api/token")
		public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken
			(@RequestBody CreateAccessTokenRequest request) {
			String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

			return ResponseEntity.status(HttpStatus.CREATED)
				.body(new CreateAccessTokenResponse(newAccessToken));
		}
	}
}
