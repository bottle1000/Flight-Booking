package flight_booking.demo.flight;

import static flight_booking.demo.domain.flight.entity.Airport.*;

import java.time.LocalDateTime;

import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanCreateResponse;
import flight_booking.demo.domain.flight.entity.Airport;

public class FlightPlanTestFixure {
	// 상수 정의
	public static final Airport departure = ICN;
	public static final Airport arrival = GMP;

	// 테스트용 요청 객체 생성 메서드
	public static FlightPlanCreateRequest createFlightPlanRequest() {
		return new FlightPlanCreateRequest(
			"ICNGMP1922",
			departure,
			arrival,
			LocalDateTime.of(2050, 1, 1, 11, 11, 11),
			LocalDateTime.of(2055, 1, 1, 11, 11, 11),
			30000
		);
	}

	// 테스트용 응답 객체 생성 메서드
	public static FlightPlanCreateResponse createFlightPlanResponse() {
		return new FlightPlanCreateResponse(
			1L,
			"ICNGMP1922",
			departure,
			arrival,
			LocalDateTime.of(2050, 1, 1, 11, 11, 11),
			LocalDateTime.of(2055, 1, 1, 11, 11, 11),
			30000,
			"ICNGMP1922"
		);
	}
}