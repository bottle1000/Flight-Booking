package flight_booking.demo.domain.flight.dto.response;

import java.time.ZonedDateTime;

import flight_booking.demo.domain.flight.entity.FlightPlan;

public record FlightPlanGetListResponse(
	Long id,
	String departure,
	String arrival,
	ZonedDateTime boardingAt,
	ZonedDateTime landingAt,
	int price
) {

	public static FlightPlanGetListResponse from(FlightPlan flightPlan) {

		return new FlightPlanGetListResponse(
			flightPlan.getId(),
			flightPlan.getDeparture().getOfficialName(),
			flightPlan.getArrival().getOfficialName(),
			flightPlan.getBoardingAt(),
			flightPlan.getLandingAt(),
			flightPlan.getPrice()
		);
	}
}