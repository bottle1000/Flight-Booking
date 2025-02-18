package flight_booking.demo.domain.flight.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Airport {
	ICN("ICN"),
	GMP("GMP"),
	HND("HND"),
	NRT("NRT"),
	JFK("JFK");

	private final String code;
}