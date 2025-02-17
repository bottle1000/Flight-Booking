package flight_booking.demo.domain.airplane.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.domain.airplane.dto.request.AirplaneCreateRequest;
import flight_booking.demo.domain.airplane.dto.response.AirplaneCreateResponse;
import flight_booking.demo.domain.airplane.dto.response.AirplaneGetResponse;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.entity.SeatColumn;
import flight_booking.demo.domain.airplane.entity.Ticket;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.airplane.repository.TicketRepository;
import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanCreateResponse;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.repository.FlightPlanRepository;
import lombok.RequiredArgsConstructor;

import static flight_booking.demo.common.exception.ResponseCode.AIRPLANE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AirplaneService {

	private final AirplaneRepository airplaneRepository;
	private final TicketRepository ticketRepository;
	private final FlightPlanRepository flightPlanRepository;

	@Transactional
	public AirplaneCreateResponse create(AirplaneCreateRequest request) {
		Airplane airplane = Airplane.from(request.name());
		airplaneRepository.save(airplane);
		return AirplaneCreateResponse.from(airplane);
	}

	/**
	 * SINWOO
	 * CreateFlightPlan 은 FlightPlan Controller 에서 진행해야합니다.
	 */
	@Transactional
	public FlightPlanCreateResponse createFlightPlan(Long airplaneId, FlightPlanCreateRequest flightPlanCreateRequest) {

		Airplane foundAirplane = airplaneRepository.findById(airplaneId)
			.orElseThrow(() -> new CustomException(AIRPLANE_NOT_FOUND));

		// todo 항공 스케쥴 검증 메서드 ( 구현 중 )
		// existsOverlappingSchedule(foundAirplane, flightPlanCreateRequest);

		FlightPlan newFlightPlan = FlightPlan.create(
			flightPlanCreateRequest.departure(),
			flightPlanCreateRequest.arrival(),
			flightPlanCreateRequest.price(),
			flightPlanCreateRequest.boardingAt(),
			flightPlanCreateRequest.landingAt(),
			foundAirplane
		);

		for (int row = 1; row <= 10; row++) {
			for (SeatColumn column : SeatColumn.values()) {
				Ticket ticket = new Ticket(row + column.name(), newFlightPlan);
				ticketRepository.save(ticket);
			}
		}
		FlightPlan savedFlightPlan = flightPlanRepository.save(newFlightPlan);
		return FlightPlanCreateResponse.from(savedFlightPlan);
	}

	public Page<AirplaneGetResponse> getAirplaneList(Pageable pageable) {
		Page<Airplane> airplaneList = airplaneRepository.findAll(pageable);
		return airplaneList.map(airplane -> new AirplaneGetResponse(airplane.getId(), airplane.getName()));
	}

}
