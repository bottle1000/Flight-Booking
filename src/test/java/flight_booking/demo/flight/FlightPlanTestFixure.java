package flight_booking.demo.flight;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanCreateResponse;
import flight_booking.demo.domain.flight.entity.Airport;

public class FlightPlanTestFixure {

	// 테스트용 요청 객체 생성 메서드
	public static FlightPlanCreateRequest createFlightPlanRequest() {
		return new FlightPlanCreateRequest(
			"ICNGMP3000",
			Airport.ICN,
			Airport.GMP,
			ZonedDateTime.of(
				2050, 3, 1, 1, 1, 1, 1, ZoneId.of("Asia/Seoul")
			),
			ZonedDateTime.of(
				2051, 3, 1, 1, 1, 1, 1, ZoneId.of("Asia/Seoul")
			),
			30000
		);
	}

	// 테스트용 응답 객체 생성 메서드
	public static FlightPlanCreateResponse createFlightPlanResponse() {
		return new FlightPlanCreateResponse(
			1L,
			"ICNGMP1922",
			Airport.ICN,
			Airport.GMP,
			ZonedDateTime.of(
				2050, 3, 1, 1, 1, 1, 0, ZoneId.of("Asia/Seoul")
			),
			ZonedDateTime.of(
				2051, 3, 1, 1, 1, 1, 0, ZoneId.of("Asia/Seoul")
			),
			30000,
			"ICNGMP1922"
		);
	}
}