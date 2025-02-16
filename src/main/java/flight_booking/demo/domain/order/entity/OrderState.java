package flight_booking.demo.domain.order.entity;

import java.util.Arrays;

public enum OrderState {
    NONE("NONE"),
    NOT_PAID("NOT_PAID"),
    PAID("PAID"),
    CANCELING("CANCELING"),
    CANCELED("CANCELED");

    public final String code;
    OrderState(String code) {
        this.code = code;
    }

    public static OrderState fromString(String status) {
        return Arrays.stream(OrderState.values())
                .filter(o -> o.toString().equalsIgnoreCase(status))
                .findFirst()
                //Todo : GlobalException
                .orElseThrow(() -> new IllegalArgumentException("결제 상태가 존재하지 않습니다."));
    }
}