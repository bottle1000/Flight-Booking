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

	public static Page<FlightPlanGetListResponse> from(Page<FlightPlan> flightPlan) {
		//todo PageImpl 직렬화 문제 있음.
		return flightPlan.map(plan -> new FlightPlanGetListResponse(
			plan.getId(),
			plan.getDeparture().getCode(),
			plan.getArrival().getCode(),
			plan.getBoardingAt(),
			plan.getLandingAt(),
			plan.getPrice()
		));
	}
}