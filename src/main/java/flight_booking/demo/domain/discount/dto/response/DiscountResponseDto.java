package flight_booking.demo.domain.discount.dto.response;

import java.time.ZonedDateTime;

import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;

public record DiscountResponseDto(
	Long id,
	DiscountType discountType,
	int rate,
	int amount,
	String description,
	ZonedDateTime startAt,
	ZonedDateTime endAt
) {
	public static DiscountResponseDto from(Discount discount) {
		return new DiscountResponseDto(
			discount.getId(),
			discount.getDiscountType(),
			discount.getRate(),
			discount.getAmount(),
			discount.getDescription(),
			discount.getStartAt(),
			discount.getEndAt()
		);
	}
}
