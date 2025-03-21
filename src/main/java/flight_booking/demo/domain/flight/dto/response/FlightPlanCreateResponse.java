package flight_booking.demo.domain.flight.dto.response;

import java.time.ZonedDateTime;

import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;

public record FlightPlanCreateResponse(
	Long id,
	String airplaneName,
	Airport departure,
	Airport arrival,
	ZonedDateTime boardingAt,
	ZonedDateTime landingAt,
	int price,
	String name
) {
	public static FlightPlanCreateResponse from(FlightPlan savedFlightPlan) {
		return new FlightPlanCreateResponse(
			savedFlightPlan.getId(),
			savedFlightPlan.getAirplane().getName(),
			savedFlightPlan.getDeparture(),
			savedFlightPlan.getArrival(),
			savedFlightPlan.getBoardingAt(),
			savedFlightPlan.getLandingAt(),
			savedFlightPlan.getPrice(),
			savedFlightPlan.getName()
		);
	}
}
