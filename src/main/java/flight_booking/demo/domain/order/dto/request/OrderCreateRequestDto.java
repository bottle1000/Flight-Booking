package flight_booking.demo.domain.order.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateRequestDto(
	@NotNull
	Long ticketId,
	List<Long> discountIds
) {
}
