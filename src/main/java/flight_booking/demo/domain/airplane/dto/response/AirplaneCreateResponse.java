package flight_booking.demo.domain.airplane.dto.response;

import flight_booking.demo.domain.airplane.entity.Airplane;

public record AirplaneCreateResponse(String name) {
	public static AirplaneCreateResponse from(Airplane savedAirplane) {
		return new AirplaneCreateResponse(savedAirplane.getName());
	}
}
