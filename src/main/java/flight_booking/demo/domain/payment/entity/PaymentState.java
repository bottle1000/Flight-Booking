package flight_booking.demo.domain.payment.entity;

import flight_booking.demo.common.exception.payment.PaymentErrorResponseCode;
import flight_booking.demo.common.exception.payment.PaymentException;

import java.util.Arrays;

public enum PaymentState {
    NONE("NONE"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETE("COMPLETE"),
    FAIL("FAIL"),
    CANCEL("CANCEL");


    public final String code;
    PaymentState(String value) {
        this.code = value;
    }

    public static PaymentState from(String code) {
        return Arrays.stream(PaymentState.values())
                .filter(o -> o.toString().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new PaymentException(PaymentErrorResponseCode.INVALID_PAYMENT_STATE));
    }
}
