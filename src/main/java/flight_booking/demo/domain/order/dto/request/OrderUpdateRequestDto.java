package flight_booking.demo.domain.order.dto.request;

import flight_booking.demo.domain.order.entity.OrderState;
import jakarta.validation.constraints.NotNull;

public record OrderUpdateRequestDto (
        @NotNull
        long ticketId
){
}
