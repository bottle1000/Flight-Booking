package flight_booking.demo.domain.discount.entity;

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

        //TODO: GlobalExceptionHandler
        throw new RuntimeException("해당 타입: " + id + " 은 지원하지 않는 할인 타입입니다 : ");
    }
}
