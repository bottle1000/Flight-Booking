package flight_booking.demo.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterRequestDto {

	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Invalid email format")
	private final String email;

	@NotBlank(message = "Password cannot be blank")
	private final String password;

}
