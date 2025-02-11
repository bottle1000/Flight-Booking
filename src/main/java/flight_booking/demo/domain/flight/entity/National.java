package flight_booking.demo.domain.flight.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum National {
	KOREA("대한민국"),
	JAPAN("일본"),
	CHINA("중국"),
	USA("미국");

	private final String description;
}
