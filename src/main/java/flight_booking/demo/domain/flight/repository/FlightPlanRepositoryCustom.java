package flight_booking.demo.domain.flight.repository;

import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface FlightPlanRepositoryCustom {
	Page<FlightPlan> findByFilters(
		Airport departure,
		Airport arrival,
		LocalDateTime boardingAt,
		LocalDateTime landingAt,
		Pageable pageable
	);
}