package flight_booking.demo.domain.discount.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record DiscountCreateRequestDto(
        @NotNull
        int typeValue,
        @NotNull
        int rate,
        @NotNull
        int amount,
        String description,
        @NotNull
        LocalDateTime startAt,
        @NotNull
        LocalDateTime endAt
) {
}
