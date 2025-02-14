package flight_booking.demo.domain.airplane;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flight_booking.demo.domain.airplane.dto.request.AirplaneCreateRequest;
import flight_booking.demo.domain.airplane.dto.response.AirplaneCreateResponse;
import flight_booking.demo.domain.airplane.dto.response.AirplaneGetResponse;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AirplaneService {

	private final AirplaneRepository airplaneRepository;

	@Transactional
	public AirplaneCreateResponse createAirplane(AirplaneCreateRequest request) {
		Airplane airplane = Airplane.from(request.name());
		airplaneRepository.save(airplane);
		return AirplaneCreateResponse.from(airplane);
	}

	public Page<AirplaneGetResponse> getAirplaneList(Pageable pageable) {
		Page<Airplane> airplaneList = airplaneRepository.findAll(pageable);
		return airplaneList.map(airplane -> new AirplaneGetResponse(airplane.getId(), airplane.getName()));
	}

}
