package flight_booking.demo.domain.order.dto.request;

import flight_booking.demo.domain.order.entity.OrderState;
import jakarta.persistence.Convert;

public record OrderUpdateRequestDto (
        int ticketId,
        @Convert(converter = OrderState.class)
        OrderState orderState
){
}
