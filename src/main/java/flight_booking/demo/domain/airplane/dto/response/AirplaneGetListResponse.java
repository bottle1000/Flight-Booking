package flight_booking.demo.domain.airplane.dto.response;

import flight_booking.demo.domain.airplane.entity.Airplane;

public record AirplaneGetListResponse(Long id, String name) {
	public static AirplaneGetListResponse from(Airplane airplane) {
		return new AirplaneGetListResponse(airplane.getId(), airplane.getName());
	}
}
