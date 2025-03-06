package flight_booking.demo.domain.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import flight_booking.demo.domain.flight.entity.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "SELECT * FROM ticket WHERE id = :ticketId", nativeQuery = true)
    Ticket findTicketByNativeQuery(@Param("ticketId") Long ticketId);
}
