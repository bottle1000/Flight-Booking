package flight_booking.demo.domain.order.dto.response;

import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.entity.OrderState;

import java.util.List;

public record OrderResponseDto(
	long id,
	List<Long> ticketIds,
	OrderState orderState,
	int price
) {
	public static OrderResponseDto from(Order order) {
		return new OrderResponseDto(
			order.getId(),
			order.getTicketIds(),
			order.getState(),
			order.getPrice()
		);
	}
}
