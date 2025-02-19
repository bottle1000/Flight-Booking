package flight_booking.demo.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentRes {
    private String orderId;
    private int amount;
    private String orderName;
}
