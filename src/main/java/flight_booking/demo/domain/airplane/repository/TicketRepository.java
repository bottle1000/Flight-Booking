package flight_booking.demo.domain.airplane.repository;

import flight_booking.demo.domain.airplane.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SINWOO
 * Ticket 은 FlightPlan 에 종속적입니다.
 * FlightPlan Repository 폴더에 있어야 합니다.
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
