package flight_booking.demo.domain.order.dto.response;

import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.entity.OrderState;

public record OrderResponseDto(
        long id,
        long ticketId,
        OrderState orderState,
        int price
) {
    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getTicket().getId(),
                order.getState(),
                order.getPrice()
        );
    }
}
