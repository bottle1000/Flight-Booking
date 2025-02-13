package flight_booking.demo.security.token.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequest {
  private String refreshToken;
}
