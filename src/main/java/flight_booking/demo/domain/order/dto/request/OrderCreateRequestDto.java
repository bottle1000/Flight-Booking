package flight_booking.demo.domain.order.dto.request;

import java.util.List;

public record OrderCreateRequestDto(
	List<Long> ticketIds,
	List<Long> discountIds
) {
}
