package flight_booking.demo.domain.discount.entity;

import flight_booking.demo.common.entity.exception.CustomException;
import flight_booking.demo.common.entity.exception.ResponseCode;

public enum DiscountType {
    NONE(0),
    ADMIN(1),
    EVENT(2),
    GRADE(3);

    public final int id;

    DiscountType(int value) {
        this.id = value;
    }

    public static DiscountType of(int id) {
        for (DiscountType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new CustomException(ResponseCode.DISCOUNT_TYPE_NOT_FOUND);
    }
}
