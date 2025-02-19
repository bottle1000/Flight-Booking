package flight_booking.demo.domain.flight.dto.request;

import flight_booking.demo.domain.flight.entity.Airport;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

public record FlightPlanGetRequest(
	@NotNull(message = "출발지는 필수입니다")
	Airport departure,
	@NotNull(message = "출발지는 필수입니다")
	Airport arrival,

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	ZonedDateTime boardingAt,

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	ZonedDateTime landingAt
) { }
