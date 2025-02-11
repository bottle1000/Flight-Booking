package flight_booking.demo.domain.flight.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import flight_booking.demo.domain.flight.entity.National;

public record FlightPlanCreateRequest(
	National fromNational,
	National toNational,
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate boardingAt,
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate landingAt
) {
	public LocalDateTime getStartDateTime() {
		return (boardingAt() != null) ? boardingAt.atStartOfDay() : null;
	}
	public LocalDateTime getEndDateTime() {
		return (landingAt != null) ? landingAt.atTime(LocalTime.MAX) : null;
	}
}
