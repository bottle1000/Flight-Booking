package flight_booking.demo.domain.flight.dto.response;

import java.time.Duration;
import java.time.ZonedDateTime;

import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;

public record FlightPlanGetListResponse(
	Long id,
	Airport departure,
	Airport arrival,
	ZonedDateTime boardingAt,
	ZonedDateTime landingAt,
	Duration flightDuration,
	int price
) {

	public static FlightPlanGetListResponse from(FlightPlan flightPlan) {

		return new FlightPlanGetListResponse(
			flightPlan.getId(),
			flightPlan.getDeparture(),
			flightPlan.getArrival(),
			flightPlan.getBoardingAt(),
			flightPlan.getLandingAt(),
			flightPlan.flightDuration(flightPlan),
			flightPlan.getPrice()
		);
	}
}