package flight_booking.demo.domain.payment.entity;

import flight_booking.demo.common.entity.exception.CustomException;
import flight_booking.demo.common.entity.exception.ResponseCode;

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
                .orElseThrow(() -> new CustomException(ResponseCode.INVALID_PAYMENT_STATE));
    }
}
