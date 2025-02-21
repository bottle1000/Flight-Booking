package flight_booking.demo.domain.user.dto.request;

import flight_booking.demo.domain.user.entity.Role;
import lombok.Getter;

@Getter
public class UpdateRoleRequestDto {
	private Role role;
}
