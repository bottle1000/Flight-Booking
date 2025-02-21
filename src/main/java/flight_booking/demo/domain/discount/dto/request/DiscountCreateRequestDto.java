package flight_booking.demo.domain.discount.dto.request;

import java.time.ZonedDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DiscountCreateRequestDto(
	@NotNull
	int typeValue,
	@NotNull
	int rate,
	@NotNull
	int amount,
	String description,
	@NotNull
	ZonedDateTime startAt,
	@NotNull
	@Future(message = "종료 날짜는 지난 날짜로 설정할 수 없습니다.")
	ZonedDateTime endAt
) {
}
