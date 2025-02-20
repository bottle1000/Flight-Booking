package flight_booking.demo.domain.airplane.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flight_booking.demo.domain.airplane.dto.request.AirplaneCreateRequest;
import flight_booking.demo.domain.airplane.dto.response.AirplaneCreateResponse;
import flight_booking.demo.domain.airplane.dto.response.AirplaneGetListResponse;
import flight_booking.demo.domain.airplane.service.AirplaneService;
import flight_booking.demo.utils.Page;
import flight_booking.demo.utils.PageQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AirplaneController {

	private final AirplaneService airplaneService;

	@PostMapping("/admin/airplanes")
	public ResponseEntity<AirplaneCreateResponse> createAirplane(
		@Valid @RequestBody AirplaneCreateRequest request) {
		AirplaneCreateResponse response = airplaneService.create(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/airplanes")
	public ResponseEntity<Page<AirplaneGetListResponse>> findAirplaneList(PageQuery pageQuery) {
		Page<AirplaneGetListResponse> response = airplaneService.findAirplaneList(pageQuery);
		return ResponseEntity.ok(response);
	}
}
