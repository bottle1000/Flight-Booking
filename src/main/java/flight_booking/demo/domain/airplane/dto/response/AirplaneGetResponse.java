package flight_booking.demo.domain.airplane.dto.response;

import flight_booking.demo.domain.airplane.entity.Airplane;

public record AirplaneGetResponse(Long id, String name) {
	public static AirplaneGetResponse from(Airplane airplane) {
		return new AirplaneGetResponse(airplane.getId(), airplane.getName());
	}
}
