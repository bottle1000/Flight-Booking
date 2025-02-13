package flight_booking.demo.domain.flight.entity;

import com.fasterxml.jackson.annotation.JsonValue;

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

	@JsonValue
	public String getCode() {
		return code;
	}
}