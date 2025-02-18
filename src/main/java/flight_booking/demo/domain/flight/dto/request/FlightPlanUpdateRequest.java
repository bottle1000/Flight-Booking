package flight_booking.demo.domain.flight.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import flight_booking.demo.domain.flight.entity.Airport;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * SINWOO
 * 항공스케줄이 생성된 후의 Update Flow 에 대한 고찰이 필요해 보입니다. -> 통합 이후 행해져야할 작업입니다. 멍청한 첨언이었다고 생각해요.
 *
 * 1. 항공스케줄의 Update 가 가능한 시점은 언제인가
 * 2. 만약 Update 된다면, 어떤 Property 들이 Update 될 수 있는가
 * 3. Update 이후, 처리해야 될 것은 무엇인가
 */
public record FlightPlanUpdateRequest(

	@NotNull(message = "출발지는 필수입니다")
	Airport departure,
	@NotNull(message = "도착지는 필수입니다")
	Airport arrival,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Future(message = "탑승 시간은 미래 시간이어야 합니다")
	LocalDateTime boardingAt,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime landingAt) {
}
