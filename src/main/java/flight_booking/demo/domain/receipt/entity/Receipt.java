package flight_booking.demo.domain.receipt.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Receipt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Order order;

    private int originalPrice;
    private int discountedPrice;

    public Receipt(Order order, int originalPrice, int discountedPrice) {
        this.order = order;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
    }
}
