package flight_booking.demo.domain.flight.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.entity.FlightPlan;

public interface FlightPlanRepository extends JpaRepository<FlightPlan, Long>, FlightPlanRepositoryCustom {

	boolean existsByAirplaneIdAndName(Long airplaneId, String name);
}
