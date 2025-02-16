package flight_booking.demo.domain.airplane.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatState {
	NONE("NONE"),
	IDLE("이용가능"),
	BOOKED("예약됨"),
	UNAVAILABLE("이용불가");

	private final String description;
}
