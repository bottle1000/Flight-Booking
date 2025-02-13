package flight_booking.demo.domain.airplane;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flight_booking.demo.domain.airplane.dto.request.AirplaneCreateRequest;
import flight_booking.demo.domain.airplane.dto.response.AirplaneCreateResponse;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/airplanes")
public class AirplaneController {

	private final AirplaneRepository airplaneRepository;

	@PostMapping
	public ResponseEntity<AirplaneCreateResponse> createAirplane(@Valid  @RequestBody AirplaneCreateRequest request) {
		Airplane airplane = Airplane.create(request.name());
		Airplane savedAirplane = airplaneRepository.save(airplane);
		AirplaneCreateResponse response = AirplaneCreateResponse.from(savedAirplane);
		return ResponseEntity.ok().body(response);
	}
}
