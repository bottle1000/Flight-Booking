package flight_booking.demo.domain.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import flight_booking.demo.domain.flight.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
