package flight_booking.demo.domain.order.entity;

public enum OrderState {
    NONE("NONE"),
    NOT_PAID("NOT_PAID"),
    PAID("PAID"),
    CANCEL("CANCEL");


    public final String code;
    OrderState(String code) {
        this.code = code;
    }
}
