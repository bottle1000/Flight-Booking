package flight_booking.demo.domain.discount.dto.request;

import flight_booking.demo.domain.discount.entity.DiscountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DiscountCreateRequest(
        @NotNull
        DiscountType discountType,
        @NotNull
        int rate,
        @NotNull
        int amount,
        /**
         * JUNIL
         * 1. description 은 설명란이므로, 공백일 수도 있습니다. 공백을 허용하지 않는 것이 의도일 경우 해당 주석은 무시하셔도 됩니다.
         * 2. started_at 과 end_at 은 네이밍컨벤션이 다른 컨벤션으로 보입니다. 수정 바랍니다.
         * 3.
         * end_at 의 경우, Entity 에서는 기본값을 null 로 설정중입니다.
         * 하지만 해당 CreateDTO 에서는 @NotNull 어노테이션으로 null 값을 허용하고 있지 않습니다.
         * 해당 부분은 서로 모순되므로 DTO 혹은 Entity 를 수정하여 생성 흐름을 명확히 하여야 합니다.
         */
        @NotBlank
        String description,
        @NotNull
        LocalDateTime started_at,
        @NotNull
        LocalDateTime end_at
) {
}
