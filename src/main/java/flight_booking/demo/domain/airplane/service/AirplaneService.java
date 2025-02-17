package flight_booking.demo.domain.airplane.service;

import static flight_booking.demo.common.entity.exception.ResponseCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flight_booking.demo.common.entity.exception.CustomException;
import flight_booking.demo.domain.airplane.dto.request.AirplaneCreateRequest;
import flight_booking.demo.domain.airplane.dto.response.AirplaneCreateResponse;
import flight_booking.demo.domain.airplane.dto.response.AirplaneGetResponse;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.entity.SeatColumn;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.flight.repository.TicketRepository;
import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanCreateResponse;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.repository.FlightPlanRepository;
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


	public Page<AirplaneGetResponse> findAirplaneList(Pageable pageable) {
		Page<Airplane> airplaneList = airplaneRepository.findAll(pageable);
		return airplaneList.map(airplane -> new AirplaneGetResponse(airplane.getId(), airplane.getName()));
	}

}
