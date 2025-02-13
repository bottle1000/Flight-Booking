package flight_booking.demo.domain.discount.dto.request;

import java.time.LocalDateTime;

public record DiscountEndAtUpdateRequest(
        LocalDateTime end_at
) {
}
