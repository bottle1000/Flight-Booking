package flight_booking.demo.domain.payment.entity;


import java.util.Arrays;

public enum PaymentState {
    NONE,
    IN_PROGRESS,
    COMPLETE,
    FAIL,
    CANCEL;

    public static PaymentState fromString(String status) {
        return Arrays.stream(PaymentState.values())
                .filter(p -> p.toString().equalsIgnoreCase(status))
                .findFirst()
                //Todo : GlobalException
                .orElseThrow(() -> new IllegalArgumentException("결제 상태가 존재하지 않습니다."));
    }
}
