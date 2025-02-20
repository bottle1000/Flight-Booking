package flight_booking.demo.domain.discount.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record DiscountCreateRequestDto(
        @NotNull
        int typeValue,
        @NotNull
        int rate,
        @NotNull
        int amount,
        String description,
        @NotNull
        LocalDateTime startAt,
        @NotNull
        @Future(message = "종료 날짜는 지난 날짜로 설정할 수 없습니다.")
        LocalDateTime endAt
) {
}
