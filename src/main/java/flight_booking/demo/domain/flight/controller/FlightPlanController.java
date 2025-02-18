package flight_booking.demo.domain.flight.controller;

import flight_booking.demo.common.ApiResponse;
import flight_booking.demo.domain.flight.dto.request.FlightPlanGetRequest;
import flight_booking.demo.domain.flight.dto.request.FlightPlanUpdateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanGetResponse;
import flight_booking.demo.domain.flight.dto.response.FlightPlaneUpdateResponse;
import flight_booking.demo.domain.flight.service.FlightPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight-plans")
@RequiredArgsConstructor
public class FlightPlanController {

	private final FlightPlanService flightPlanService;

	@GetMapping
	public ResponseEntity<ApiResponse<Page<FlightPlanGetResponse>>> findFlightPlanPage(
		@Valid @ModelAttribute FlightPlanGetRequest flightPlanGetRequest,
		@PageableDefault Pageable pageable
	) {
		Page<FlightPlanGetResponse> response = flightPlanService.findFilteredFlightsPlanPage(
			flightPlanGetRequest,
			pageable
		);
		return ResponseEntity.ok(ApiResponse.success("항공 스케쥴 목록을 성공적으로 조회했습니다", response));
	}

	/**
	 * SINWOO
	 * 해당 메소드는 ADMIN 전용으로 보입니다.
	 * 1. Admin 전용 컨트롤러를 생성하거나,
	 * 2. ADMIN 전용 URL 로 설정하거나,
	 * 3. FlightPlan Service 에서 Role 확인 작업을 진행해주시기 바랍니다.
	 */
	@PutMapping("/{flight-plan_id}")
	public ResponseEntity<ApiResponse<FlightPlaneUpdateResponse>> updateFlightPlan(
		@PathVariable("flight-plan_id") Long flightPlanId,
		@Valid @RequestBody FlightPlanUpdateRequest flightPlanUpdateRequest
	) {
		FlightPlaneUpdateResponse response = flightPlanService.updateFlightPlan(flightPlanId, flightPlanUpdateRequest);
		return ResponseEntity.ok(ApiResponse.success("항공 스케쥴이 성공적으로 수정되었습니다.", response));
	}
}
