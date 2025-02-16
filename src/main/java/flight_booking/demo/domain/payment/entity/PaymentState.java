package flight_booking.demo.domain.payment.entity;

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
                //TODO: GlobalHandlerException
                .orElseThrow(() -> new IllegalArgumentException("결제 상태가 존재하지 않습니다."));
    }
}
