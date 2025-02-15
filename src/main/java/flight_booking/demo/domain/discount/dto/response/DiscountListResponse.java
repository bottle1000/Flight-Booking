package flight_booking.demo.domain.discount.dto.response;

import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;

import java.time.LocalDateTime;

public record DiscountListResponse(
        int id,
        DiscountType discountType,
        int rate,
        int amount,
        String description,
        LocalDateTime started_at,
        LocalDateTime end_at
) {
    public static DiscountListResponse from(Discount discount) {
        return new DiscountListResponse(
                discount.getId(),
                discount.getDiscountType(),
                discount.getRate(),
                discount.getAmount(),
                discount.getDescription(),
                discount.getStart_at(),
                discount.getEnd_at()
        );
    }
}
