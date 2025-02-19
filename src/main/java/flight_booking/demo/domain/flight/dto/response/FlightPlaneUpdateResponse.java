package flight_booking.demo.domain.flight.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;

import java.time.ZonedDateTime;

public record FlightPlaneUpdateResponse(
	Long id,
	String airplaneName,
	Airport departure,
	Airport arrival,
	ZonedDateTime boardingAt,
	ZonedDateTime landingAt,
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