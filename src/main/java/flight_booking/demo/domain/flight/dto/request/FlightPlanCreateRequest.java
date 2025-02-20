package flight_booking.demo.domain.flight.dto.request;

import java.time.ZonedDateTime;

import flight_booking.demo.domain.flight.entity.Airport;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record FlightPlanCreateRequest(
	String name,
	@NotNull(message = "출발지는 필수입니다")
	Airport departure,
	@NotNull(message = "도착지는 필수입니다")
	Airport arrival,

	@Future(message = "탑승 시간은 미래 시간이어야 합니다")
	ZonedDateTime boardingAt,
	ZonedDateTime landingAt,

	int price
) {
}
