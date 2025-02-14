package flight_booking.demo.domain.flight.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import flight_booking.demo.domain.flight.entity.FlightPlan;

public interface FlightPlanRepositoryCustom {
	Page<FlightPlan> findByFilters(
		String departure,
		String arrival,
		LocalDateTime boardingAt,
		LocalDateTime landingAt,
		Pageable pageable
	);
}