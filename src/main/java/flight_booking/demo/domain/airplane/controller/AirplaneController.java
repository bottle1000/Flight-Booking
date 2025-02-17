package flight_booking.demo.domain.airplane.controller;

import flight_booking.demo.domain.airplane.service.AirplaneService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flight_booking.demo.common.ApiResponse;
import flight_booking.demo.domain.airplane.dto.request.AirplaneCreateRequest;
import flight_booking.demo.domain.airplane.dto.response.AirplaneCreateResponse;
import flight_booking.demo.domain.airplane.dto.response.AirplaneGetResponse;
import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/airplanes")
public class AirplaneController {

	private final AirplaneService airplaneService;

	@PostMapping
	public ResponseEntity<AirplaneCreateResponse> createAirplane(
		@Valid @RequestBody AirplaneCreateRequest request) {
		AirplaneCreateResponse response = airplaneService.create(request);
		return ResponseEntity.ok(response);
	}


	@GetMapping
	public ResponseEntity<Page<AirplaneGetResponse>> findAirplaneList(
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	) {
		Page<AirplaneGetResponse> response = airplaneService.findAirplaneList(pageable);
		return ResponseEntity.ok(response);
	}
}
