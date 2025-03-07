package flight_booking.demo.domain.order.dto.request;

import java.util.List;

public record OrderUpdateRequestDto(
	List<Long> ticketIds
) {
}
