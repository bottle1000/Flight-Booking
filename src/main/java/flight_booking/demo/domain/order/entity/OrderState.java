package flight_booking.demo.domain.order.entity;

import flight_booking.demo.common.exception.CustomException;

import java.util.Arrays;

import static flight_booking.demo.common.exception.ResponseCode.INVALID_ORDER_STATE;

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

    public static OrderState from(String code) {
        return Arrays.stream(OrderState.values())
                .filter(o -> o.toString().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new CustomException(INVALID_ORDER_STATE));
    }
}