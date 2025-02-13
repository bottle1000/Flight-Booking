package flight_booking.demo.domain.payment.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum PaymentState {
    NONE(0),
    IN_PROGRESS(1),
    COMPLETE(2),
    FAIL(3),
    CANCEL(4);


    public final int id;

    PaymentState(int value) {
        this.id = value;
    }

    public static PaymentState of(int id) {
        for (PaymentState state : values()) {
            if (state.id == id) {
                return state;
            }
        }

        //TODO: GlobalExceptionHandler
        throw new RuntimeException("해당 타입: " + id + " 은 지원하지 않는 주문상태입니다 : ");
    }

    @Converter
    static class Convertor implements AttributeConverter<PaymentState, Integer> {
        @Override
        public Integer convertToDatabaseColumn(PaymentState state) {
            return state.id;
        }
        @Override
        public PaymentState convertToEntityAttribute(Integer id) {
            return of(id);
        }
    }
}
