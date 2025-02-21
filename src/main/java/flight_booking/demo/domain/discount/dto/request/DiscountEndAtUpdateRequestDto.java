package flight_booking.demo.domain.discount.dto.request;

import java.time.ZonedDateTime;

import jakarta.validation.constraints.NotNull;

public record DiscountEndAtUpdateRequestDto(
	@NotNull
	ZonedDateTime endAt
) {
}
