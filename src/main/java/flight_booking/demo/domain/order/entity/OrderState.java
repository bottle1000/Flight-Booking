package flight_booking.demo.domain.order.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum OrderState {
    NONE(0),
    NOT_PAID(1),
    PAID(2),
    CANCELING(3),
    CANCELED(4);


    public final int id;

    OrderState(int value) {
        this.id = value;
    }

    public static OrderState of(int id) {
        for (OrderState state : values()) {
            if (state.id == id) {
                return state;
            }
        }

        //TODO: GlobalExceptionHandler
        throw new RuntimeException("해당 타입: " + id + " 은 지원하지 않는 주문상태입니다 : ");
    }

    @Converter
    static class Convertor implements AttributeConverter<OrderState, Integer> {

        @Override
        public Integer convertToDatabaseColumn(OrderState state) {
            return state.id;
        }
        @Override
        public OrderState convertToEntityAttribute(Integer id) {
            return of(id);
        }
    }
}
