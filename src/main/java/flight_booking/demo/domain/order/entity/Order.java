package flight_booking.demo.domain.order.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.ticket.entity.Ticket;
import flight_booking.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Ticket ticket;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderState state = OrderState.NOT_PAID;

    private int price;

    public Order(User user, Ticket ticket, int price) {
        this.user = user;
        this.ticket = ticket;
        this.price = price;
        this.payment = new Payment(this);
    }

    public void changeTicket(Ticket ticket) {
        if(this.ticket.getId() == ticket.getId()) {
            return;
        }
        /**
         * TODO
         * this.ticket.updateState(BOOKED to IDLE);
         * this.ticket = ticket;
         * this.ticket.updateState(IDLE to BOOKED);
         */
    }

    public void updateState(OrderState orderState) {
        this.state = orderState;
    }
}