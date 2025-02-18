package flight_booking.demo.domain.discount.dto.response;

import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;

import java.time.LocalDateTime;

/**
 * 1.
 * ResponseDto 객체의 네이밍컨벤션이 지켜지지 않은것으로 보입니다.
 * DTO 클래스는 이름의 가장 뒤에 Dto 를 붙여주기기 바랍니다.
 *
 * 2.
 * 해당 DTO 객체는 List 형태가 아니지만, 이름에는 List 가 들어가 있습니다.
 * 어떠한 의도에서 해당 네이밍으로 정했는지 확인이 필요합니다.
 */
public record DiscountListResponse(
        Long id,
        DiscountType discountType,
        int rate,
        int amount,
        String description,
        LocalDateTime started_at,
        LocalDateTime end_at
) {
    public static DiscountListResponse from(Discount discount) {
        return new DiscountListResponse(
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
