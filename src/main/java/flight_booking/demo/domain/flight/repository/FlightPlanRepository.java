package flight_booking.demo.domain.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import flight_booking.demo.domain.flight.entity.FlightPlan;

public interface FlightPlanRepository extends JpaRepository<FlightPlan, Long>, FlightPlanRepositoryCustom  {
}
