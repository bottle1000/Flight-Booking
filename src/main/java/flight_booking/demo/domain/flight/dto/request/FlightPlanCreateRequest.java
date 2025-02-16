package flight_booking.demo.domain.flight.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import flight_booking.demo.domain.flight.entity.Airport;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

/**
 * SINWOO
 * 현재 모든 RequestDto 클래스가 네이밍 이외에 같은 클래스인것으로 보입니다.
 * 통합 바랍니다.
 */
public record FlightPlanCreateRequest(
	@NotNull(message = "출발지는 필수입니다")
	Airport departure,
	@NotNull(message = "도착지는 필수입니다")
	Airport arrival,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Future(message = "탑승 시간은 미래 시간이어야 합니다")
	LocalDateTime boardingAt,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime landingAt,
	int price
) {
}
