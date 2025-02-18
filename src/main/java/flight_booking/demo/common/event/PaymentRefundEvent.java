package flight_booking.demo.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentRefundEvent {
    private final String orderId;
}
