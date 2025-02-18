package flight_booking.demo.domain.discount.dto.response;

import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;

import java.time.LocalDateTime;

/**
 *
 * JUNIL
 * DiscountCreateResponse 와 DiscountEndAtResponse 는 네이밍 이외에는 완전히 동일한 클래스로 보입니다.
 * 통합 바랍니다.
 */
public record DiscountCreateResponse(
        Long id,
        DiscountType discountType,
        int rate,
        int amount,
        String description,
        LocalDateTime started_at,
        LocalDateTime end_at
) {
    public static DiscountCreateResponse from(Discount discount) {
        return new DiscountCreateResponse(
                discount.getId(),
                discount.getDiscountType(),
                discount.getRate(),
                discount.getAmount(),
                discount.getDescription(),
                discount.getStart_at(),
                discount.getEnd_at()
        );
    }
}
