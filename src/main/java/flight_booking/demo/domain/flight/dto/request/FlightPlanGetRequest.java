package flight_booking.demo.domain.flight.dto.request;

import java.time.ZonedDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import flight_booking.demo.domain.flight.entity.Airport;
import jakarta.validation.constraints.NotNull;

public record FlightPlanGetRequest(
	@NotNull(message = "출발지는 필수입니다")
	Airport departure,
	@NotNull(message = "출발지는 필수입니다")
	Airport arrival,

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	ZonedDateTime boardingAt,

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	ZonedDateTime landingAt
) {
}
