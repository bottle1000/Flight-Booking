package flight_booking.demo.domain.discount.dto.request;

import flight_booking.demo.domain.discount.entity.DiscountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DiscountCreateRequest(
        @NotNull
        DiscountType discountType,
        @NotNull
        int rate,
        @NotNull
        int amount,
        @NotBlank
        String description,
        @NotNull
        LocalDateTime started_at,
        @NotNull
        LocalDateTime end_at
) {
}
