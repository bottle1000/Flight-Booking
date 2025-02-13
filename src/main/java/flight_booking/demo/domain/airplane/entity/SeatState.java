package flight_booking.demo.domain.airplane.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatState {
	AVAILABLE("이용가능"),
	UNAVAILABLE("이용불가"),
	OCCUPIED("점유중");

	private final String description;
}
