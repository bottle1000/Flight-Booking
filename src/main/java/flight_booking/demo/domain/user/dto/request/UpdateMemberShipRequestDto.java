package flight_booking.demo.domain.user.dto.request;

import flight_booking.demo.domain.user.entity.MemberShip;
import lombok.Getter;

@Getter
public class UpdateMemberShipRequestDto {
	private MemberShip memberShip;
}
