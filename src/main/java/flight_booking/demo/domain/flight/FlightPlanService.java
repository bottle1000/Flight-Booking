package flight_booking.demo.domain.flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.National;
import flight_booking.demo.domain.flight.repository.FlightPlanRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightPlanService {

	private final FlightPlanRepository flightPlanRepository;

	public Page<FlightPlan> getFilteredFlightsPlanList(National from, National to, LocalDate boardingAt,
		LocalDate landingAt, Pageable pageable) {

		LocalDateTime startOfDay = boardingAt != null ?
			boardingAt.atStartOfDay() : null;
		LocalDateTime endOfDay = landingAt != null ?
			landingAt.atTime(LocalTime.MAX) : null;

		return flightPlanRepository.findByFilters(from, to, startOfDay, endOfDay, pageable);
	}
}
