package flight_booking.demo.domain.flight.dto.response;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import flight_booking.demo.domain.flight.entity.FlightPlan;

public record FlightPlanGetListResponse(
	Long id,
	String departure,
	String arrival,
	LocalDateTime boardingAt,
	LocalDateTime landingAt,
	int price
) {

	public static FlightPlanGetListResponse from(FlightPlan flightPlan) {

		return new FlightPlanGetListResponse(
			flightPlan.getId(),
			flightPlan.getDeparture().getCode(),
			flightPlan.getArrival().getCode(),
			flightPlan.getBoardingAt(),
			flightPlan.getLandingAt(),
			flightPlan.getPrice()
		);
	}
}