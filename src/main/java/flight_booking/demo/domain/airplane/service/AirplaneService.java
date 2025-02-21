package flight_booking.demo.domain.airplane.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flight_booking.demo.domain.airplane.dto.request.AirplaneCreateRequest;
import flight_booking.demo.domain.airplane.dto.response.AirplaneCreateResponse;
import flight_booking.demo.domain.airplane.dto.response.AirplaneGetListResponse;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.utils.Page;
import flight_booking.demo.utils.PageQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AirplaneService {

	private final AirplaneRepository airplaneRepository;

	@Transactional
	public AirplaneCreateResponse create(AirplaneCreateRequest request) {
		Airplane airplane = Airplane.from(request.name());
		airplaneRepository.save(airplane);
		return AirplaneCreateResponse.from(airplane);
	}

	public Page<AirplaneGetListResponse> findAirplaneList(PageQuery pageQuery) {
		org.springframework.data.domain.Page<Airplane> page = airplaneRepository.findAirplaneList(
			pageQuery.toPageable()
		);
		return Page.from(page.map(AirplaneGetListResponse::from));
	}
}
