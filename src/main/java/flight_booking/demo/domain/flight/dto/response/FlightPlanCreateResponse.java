package flight_booking.demo.domain.flight.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;


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
