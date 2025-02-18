package flight_booking.demo.domain.flight.repository;

import flight_booking.demo.domain.flight.entity.FlightPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightPlanRepository extends JpaRepository<FlightPlan, Long>, FlightPlanRepositoryCustom {
}
