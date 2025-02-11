package flight_booking.demo.domain.flight.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.National;

public interface FlightPlanRepositoryCustom {
	Page<FlightPlan> findByFilters(National from, National to, LocalDateTime boardingAt, LocalDateTime landingAt, Pageable pageable);

}
