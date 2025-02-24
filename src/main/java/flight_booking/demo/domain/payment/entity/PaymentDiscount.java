package flight_booking.demo.domain.payment.entity;

import flight_booking.demo.domain.discount.entity.Discount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDiscount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "discount_id", nullable = false)
	private Discount discount;

	public PaymentDiscount(Payment payment, Discount discount) {
		this.payment = payment;
		this.discount = discount;
	}
}
