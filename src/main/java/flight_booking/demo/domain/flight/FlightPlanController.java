package flight_booking.demo.domain.flight;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import flight_booking.demo.common.ApiResponse;
import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.dto.request.FlightPlanUpdateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanCreateResponse;
import flight_booking.demo.domain.flight.dto.response.FlightPlanGetResponse;
import flight_booking.demo.domain.flight.dto.response.FlightPlaneUpdateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/flightplans")
@RequiredArgsConstructor
public class FlightPlanController {

	private final FlightPlanService flightPlanService;


	// todo : modelAttribute 사용해서 개선 해야 함.
	@GetMapping()
	public ResponseEntity<ApiResponse<Page<FlightPlanGetResponse>>> getFlightPlanList(
		@Valid @RequestParam(required = false) String departure,
		@Valid @RequestParam(required = false) String arrival,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime boardingAt,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime landingAt,
		@PageableDefault Pageable pageable
	) {
		Page<FlightPlanGetResponse> response = flightPlanService.getFilteredFlightsPlanList(departure, arrival,
			boardingAt,
			landingAt, pageable);
		return ResponseEntity.ok(ApiResponse.success("항공 스케쥴 목록을 성공적으로 조회했습니다", response));
	}


	@PostMapping
	public ResponseEntity<ApiResponse<FlightPlanCreateResponse>> createFlightPlan(
		@Valid @RequestBody FlightPlanCreateRequest flightPlanCreateRequest
	) {
		FlightPlanCreateResponse response = flightPlanService.createFlightPlan(flightPlanCreateRequest);

		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(response.id())
			.toUri();

		return ResponseEntity
			.created(location)
			.body(ApiResponse.success("항공 스케쥴이 성공적으로 등록되었습니다", response));
	}


	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<FlightPlaneUpdateResponse>> updateFlightPlan(
		@PathVariable Long id,
		@Valid @RequestBody FlightPlanUpdateRequest flightPlanUpdateRequest
	) {
		FlightPlaneUpdateResponse response = flightPlanService.updateFlightPlan(id, flightPlanUpdateRequest);
		return ResponseEntity.ok(ApiResponse.success("항공 스케쥴이 성공적으로 수정되었습니다.", response));
	}
}
