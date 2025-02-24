package flight_booking.demo.domain.discount.entity;

import java.time.ZonedDateTime;

import flight_booking.demo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Discount extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private DiscountType discountType;

	private int rate;
	private int amount;
	private String description;
	private ZonedDateTime startAt;
	private ZonedDateTime endAt;

	public Discount(
		DiscountType discountType,
		int rate,
		int amount,
		String description,
		ZonedDateTime startAt,
		ZonedDateTime endAt
	) {
		this.discountType = discountType;
		this.rate = rate;
		this.amount = amount;
		this.description = description;
		this.startAt = startAt;
		this.endAt = endAt;
	}

	public void closeAt(ZonedDateTime endAt) {
		this.endAt = endAt;
	}
}
