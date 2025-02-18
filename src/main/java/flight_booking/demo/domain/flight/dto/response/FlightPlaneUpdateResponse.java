package flight_booking.demo.domain.flight.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;

import java.time.LocalDateTime;

public record FlightPlaneUpdateResponse(
	Long id,
	String airplaneName,
	Airport departure,
	Airport arrival,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime boardingAt,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime landingAt,
	int price
) {
	public static FlightPlaneUpdateResponse from(FlightPlan foundFlightPlan) {
		return new FlightPlaneUpdateResponse(
			foundFlightPlan.getId(),
			foundFlightPlan.getAirplane().getName(),
			foundFlightPlan.getDeparture(),
			foundFlightPlan.getArrival(),
			foundFlightPlan.getBoardingAt(),
			foundFlightPlan.getLandingAt(),
			foundFlightPlan.getPrice());
	}
}