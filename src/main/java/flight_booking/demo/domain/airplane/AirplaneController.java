package flight_booking.demo.domain.airplane;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/airplanes")
public class AirplaneController {

	private final AirplaneService airplaneService;

	@PostMapping
	public ResponseEntity<ApiResponse<AirplaneCreateResponse>> createAirplane(@Valid  @RequestBody AirplaneCreateRequest request) {
		AirplaneCreateResponse response = airplaneService.createAirplane(request);
		return ResponseEntity.ok(ApiResponse.success("항공기가 성공적으로 등록되었습니다.", response));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<AirplaneGetResponse>>> getAirplaneList(
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	) {
		Page<AirplaneGetResponse> response = airplaneService.getAirplaneList(pageable);
		return ResponseEntity.ok(ApiResponse.success("항공기 목록 조회 성공", response));
	}
}
