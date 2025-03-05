package flight_booking.demo.domain.flight.controller;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.dto.request.FlightPlanGetRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanCreateResponse;
import flight_booking.demo.domain.flight.dto.response.FlightPlanGetListResponse;
import flight_booking.demo.domain.flight.dto.response.FlightPlanGetResponse;
import flight_booking.demo.domain.flight.service.FlightPlanService;
import flight_booking.demo.utils.Page;
import flight_booking.demo.utils.PageQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FlightPlanController {

	private final FlightPlanService flightPlanService;

	@PostMapping("/admin/airplanes/{airplane_id}/flight-plans")
	public ResponseEntity<FlightPlanCreateResponse> createFlightPlan(
		@PathVariable("airplane_id") Long airplaneId,
		@Valid @RequestBody FlightPlanCreateRequest flightPlanCreateRequest
	) {
		FlightPlanCreateResponse response = flightPlanService.createFlightPlan(airplaneId, flightPlanCreateRequest);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/flight-plans")
	public ResponseEntity<Page<FlightPlanGetListResponse>> findFlightPlanPage(
		@Valid @ModelAttribute FlightPlanGetRequest flightPlanGetRequest,
		@PageableDefault PageQuery pageQuery
	) {
		Page<FlightPlanGetListResponse> response = flightPlanService.findFilteredFlightsPlanPage(
			flightPlanGetRequest,
			pageQuery
		);
		return ResponseEntity.ok(response);
	}

	/**
	 * 클라이언트에게 티켓 정보를 제공하기 위해 사용되는 API입니다.
	 * @param flightPlanId
	 * @return
	 */
	@GetMapping("/flight-plans/{flight-plans_id}")
	public ResponseEntity<List<FlightPlanGetResponse>> findFlightPlan(
		@PathVariable("flight-plans_id") Long flightPlanId) {
		List<FlightPlanGetResponse> response = flightPlanService.findFlightPlan(flightPlanId);
		return ResponseEntity.ok(response);
	}
}
