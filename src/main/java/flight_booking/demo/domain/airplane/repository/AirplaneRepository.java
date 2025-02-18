package flight_booking.demo.domain.airplane.repository;

import flight_booking.demo.domain.airplane.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
}
