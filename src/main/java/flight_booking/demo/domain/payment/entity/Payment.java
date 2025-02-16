package flight_booking.demo.domain.payment.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentState state;

    @Column(nullable = false)
    private String uid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int amount;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PaymentDiscount> discounts = new HashSet<>();

    public void addDiscount(Discount discount) {
        PaymentDiscount paymentDiscount = new PaymentDiscount(this, discount);
        discounts.add(paymentDiscount);
    }

    public void removeDiscount(Discount discount) {
        discounts.removeIf(pd -> pd.getDiscount().equals(discount));
    }

    public Payment(Order order) {
        this.order = order;
        this.uid = UUID.randomUUID().toString();
        //TODO: this.name = order.getTicket().getFlightPlan().getDescription() + order.getTicket().getSeat();
        this.name = "";
        this.state = PaymentState.IN_PROGRESS;
        this.amount = order.getPrice();
    }

    public void updatePaymentStatus(String string) {
        this.state = PaymentState.fromString(string);
    }

}
