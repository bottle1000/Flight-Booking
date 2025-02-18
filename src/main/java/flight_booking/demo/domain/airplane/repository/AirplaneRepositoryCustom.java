package flight_booking.demo.domain.airplane.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import flight_booking.demo.domain.airplane.entity.Airplane;

public interface AirplaneRepositoryCustom {

	Page<Airplane> findAirplaneList(Pageable pageable);
}
