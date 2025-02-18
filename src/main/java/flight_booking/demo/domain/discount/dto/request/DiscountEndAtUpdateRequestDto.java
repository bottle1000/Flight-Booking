package flight_booking.demo.domain.discount.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DiscountEndAtUpdateRequestDto(
        @NotNull
        LocalDateTime endAt
) {
}
