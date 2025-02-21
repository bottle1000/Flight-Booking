package flight_booking.demo.domain.flight.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.Ticket;

public interface FlightPlanRepositoryCustom {
	Page<FlightPlan> findByFilters(
		Airport departure,
		Airport arrival,
		ZonedDateTime boardingAt,
		ZonedDateTime landingAt,
		Pageable pageable
	);

	List<Ticket> findTicketInfoByFlightPlanId(Long flightPlanId);

}