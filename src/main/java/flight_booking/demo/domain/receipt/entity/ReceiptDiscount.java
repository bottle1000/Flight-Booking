package flight_booking.demo.domain.receipt.entity;

import flight_booking.demo.domain.discount.entity.Discount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false)
    private Discount discount;

    public ReceiptDiscount(Receipt receipt, Discount discount) {
        this.receipt = receipt;
        this.discount = discount;
    }
}
