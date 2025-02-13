package flight_booking.demo.domain.discount.dto.request;

import flight_booking.demo.domain.discount.entity.DiscountType;

import java.time.LocalDateTime;

public record DiscountRateUpdateRequest(
        DiscountType discountType,
        int rate,
        int amount,
        String description,
        LocalDateTime started_at,
        LocalDateTime end_at
) {
}
