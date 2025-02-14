package flight_booking.demo.domain.airplane.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import flight_booking.demo.domain.airplane.entity.Airplane;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
}
