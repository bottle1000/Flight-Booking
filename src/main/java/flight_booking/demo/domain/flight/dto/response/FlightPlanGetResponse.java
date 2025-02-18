package flight_booking.demo.domain.flight.dto.response;

import flight_booking.demo.domain.flight.entity.FlightPlan;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public record FlightPlanGetResponse(
	Long id,
	String departure,
	String arrival,
	LocalDateTime boardingAt,
	LocalDateTime landingAt,
	int price
) {

	public static Page<FlightPlanGetResponse> from(Page<FlightPlan> flightPlan) {
		//todo PageImpl 직렬화 문제 있음.
		return flightPlan.map(plan -> new FlightPlanGetResponse(
			plan.getId(),
			plan.getDeparture().getCode(),
			plan.getArrival().getCode(),
			plan.getBoardingAt(),
			plan.getLandingAt(),
			plan.getPrice()
		));
	}
}