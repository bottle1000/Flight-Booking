package flight_booking.demo.domain.airplane.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AirplaneCreateRequest(
	@NotNull(message = "이름은 필수입니다")
	@NotBlank(message = "이름은 공백일 수 없습니다")
	String name
) {
}
