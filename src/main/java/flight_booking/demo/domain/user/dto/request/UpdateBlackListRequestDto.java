package flight_booking.demo.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UpdateBlackListRequestDto {
	private final String email;
	private final Boolean isBlacklisted;

	public UpdateBlackListRequestDto(Boolean isBlacklisted, String email) {
		this.email = email;
		this.isBlacklisted = isBlacklisted;
	}

}
