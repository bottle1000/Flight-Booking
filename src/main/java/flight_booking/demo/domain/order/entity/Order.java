package flight_booking.demo.domain.order.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.domain.airplane.entity.SeatState;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.payment.entity.PaymentState;
import flight_booking.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static flight_booking.demo.common.exception.ResponseCode.*;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
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
        if (this.ticket.getId() == ticket.getId()) {
            throw new CustomException(CANNOT_CHANGE_SAME_SEAT);
        }
        this.ticket.updateState(SeatState.IDLE);

        if (ticket.getState() == SeatState.UNAVAILABLE)
            throw new CustomException(UNAVAILABLE_SEAT);
        this.ticket = ticket;
        this.ticket.updateState(SeatState.BOOKED);
    }

    public void updateState(OrderState orderState) {
        if (this.state == OrderState.CANCELED)
            throw new CustomException(ALREADY_CANCELED);

        if (this.state != OrderState.PAID && orderState == OrderState.PAID)
            if (this.payment.getState() != PaymentState.COMPLETE)
                throw new CustomException(NOT_PAID);

        this.state = orderState;
    }
}