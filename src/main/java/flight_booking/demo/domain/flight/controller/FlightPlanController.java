package flight_booking.demo.domain.flight.controller;

import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanCreateResponse;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.service.FlightPlanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flight_booking.demo.domain.flight.dto.request.FlightPlanGetRequest;
import flight_booking.demo.domain.flight.dto.request.FlightPlanUpdateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanGetListResponse;
import flight_booking.demo.domain.flight.dto.response.FlightPlaneUpdateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping//("/flight-plans")
@RequiredArgsConstructor
public class FlightPlanController {

	private final FlightPlanService flightPlanService;

	@PostMapping("admin/airplanes/{airplane_id}/flight-plans")
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
		@PageableDefault Pageable pageable
	) {
		Page<FlightPlanGetListResponse> response = flightPlanService.findFilteredFlightsPlanPage(
			flightPlanGetRequest,
			pageable
		);
		return ResponseEntity.ok(response);
	}


	// @GetMapping("/flight-plans/{flight-plans_id}")
	// public ResponseEntity<FlightPlan> findFlightPlan(
	// 	@PathVariable("flight-plan_id") Long flightPlanId) {
	//
	// }


	@PutMapping("admin/flight-plans/{flight-plan_id}")
	public ResponseEntity<FlightPlaneUpdateResponse> updateFlightPlan(
		@PathVariable("flight-plan_id") Long flightPlanId,
		@Valid @RequestBody FlightPlanUpdateRequest flightPlanUpdateRequest
	) {
		FlightPlaneUpdateResponse response = flightPlanService.updateFlightPlan(flightPlanId, flightPlanUpdateRequest);
		return ResponseEntity.ok(response);
	}
}
