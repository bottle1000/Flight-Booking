package flight_booking.demo.domain.order.dto.request;

import jakarta.validation.constraints.NotNull;

public record OrderUpdateRequestDto(
	@NotNull
	long ticketId
) {
}
