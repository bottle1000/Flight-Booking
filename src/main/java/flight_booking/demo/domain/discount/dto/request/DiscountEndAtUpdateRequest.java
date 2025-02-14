package flight_booking.demo.domain.discount.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DiscountEndAtUpdateRequest(
        @NotNull
        LocalDateTime end_at
) {
}
