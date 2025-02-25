package flight_booking.demo.common.exception.payment;

import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {
    private final PaymentErrorResponseCode code;

    public PaymentException(PaymentErrorResponseCode code) {
        super(code.getMessage());
        this.code = code;
    }
}

