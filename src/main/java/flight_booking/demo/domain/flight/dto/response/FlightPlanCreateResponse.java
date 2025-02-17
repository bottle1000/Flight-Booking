package flight_booking.demo.domain.flight.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;

/**
 * SINWOO
 * 현재 모든 ResponseDto 클래스가 네이밍 이외에 같은 클래스인것으로 보입니다.
 * 통합 바랍니다.
 */
public record FlightPlanCreateResponse(
	Long id,
	String airplaneName,
	Airport departure,
	Airport arrival,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime boardingAt,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime landingAt,
	int price,
	String description
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
			savedFlightPlan.getDescription()
		);
	}
}
