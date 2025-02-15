package flight_booking.demo.domain.payment.entity;

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
}
