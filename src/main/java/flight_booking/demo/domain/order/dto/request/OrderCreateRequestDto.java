package flight_booking.demo.domain.order.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record OrderCreateRequestDto(
	@NotNull
	Long ticketId,
	List<Long> discountIds
) {
}
