package flight_booking.demo.domain.order.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.receipt.entity.Receipt;
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

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Receipt receipt;

    private int price;

    public Order(User user, Ticket ticket, int price) {
        this.user = user;
        this.ticket = ticket;
        this.price = price;

        /**
         * TODO
         * ticket.registerOrder(this);
         */
    }

    public void registerReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}