package flight_booking.demo.domain.payment.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ServerErrorResponseCode;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.order.entity.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
		this.name = order.getTicket().getFlightPlan().getName() + order.getTicket().getSeat();
		this.state = PaymentState.IN_PROGRESS;
		this.amount = order.getPrice();
	}

	public void updatePaymentStatus(PaymentState paymentState) {
		if (this.state == PaymentState.CANCEL) {
			throw new CustomException(ServerErrorResponseCode.ALREADY_CANCELED);
			//죄송함다
		}
		if (this.state != PaymentState.CONFIRMING && paymentState == PaymentState.COMPLETE) {
			throw new CustomException(ServerErrorResponseCode.NOT_PAID);
		}
		this.state = paymentState;
	}

}
