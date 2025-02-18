package flight_booking.demo.domain.discount.dto.response;

import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;

import java.time.LocalDateTime;

public record FindDiscountResponseDto(
        Long id,
        DiscountType discountType,
        int rate,
        int amount,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt
) {
    public static FindDiscountResponseDto from(Discount discount) {
        return new FindDiscountResponseDto(
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
