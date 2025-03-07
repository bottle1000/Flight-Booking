package flight_booking.demo.domain.order.entity;

import flight_booking.demo.domain.flight.entity.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    private Order order;

    public OrderTicket(Order order, Ticket ticket) {
        this.order = order;
        this.ticket = ticket;
    }
}
