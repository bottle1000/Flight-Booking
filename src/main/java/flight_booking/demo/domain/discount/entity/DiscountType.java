package flight_booking.demo.domain.discount.entity;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ServerErrorResponseCode;

public enum DiscountType {
    NONE(0),
    ADMIN(1),
    EVENT(2),
    BASIC(3),
    PREMIUM(4),
    VIP(5);

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
        throw new CustomException(ServerErrorResponseCode.DISCOUNT_TYPE_NOT_FOUND);
    }
}
