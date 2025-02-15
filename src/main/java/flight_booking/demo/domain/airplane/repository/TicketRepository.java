package flight_booking.demo.domain.airplane.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import flight_booking.demo.domain.airplane.entity.Ticket;

/**
 * SINWOO
 * Ticket 은 FlightPlan 에 종속적입니다.
 * FlightPlan Repository 폴더에 있어야 합니다.
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
