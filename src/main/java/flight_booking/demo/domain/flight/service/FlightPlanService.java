package flight_booking.demo.domain.flight.service;

import static flight_booking.demo.common.entity.exception.ResponseCode.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flight_booking.demo.common.entity.exception.CustomException;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.entity.SeatColumn;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.flight.dto.response.FlightPlanCreateResponse;
import flight_booking.demo.domain.flight.dto.response.FlightPlanGetResponse;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.flight.repository.TicketRepository;
import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.dto.request.FlightPlanGetRequest;
import flight_booking.demo.domain.flight.dto.request.FlightPlanUpdateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanGetListResponse;
import flight_booking.demo.domain.flight.dto.response.FlightPlaneUpdateResponse;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.repository.FlightPlanRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlightPlanService {

	private final FlightPlanRepository flightPlanRepository;
	private final AirplaneRepository airplaneRepository;
	private final TicketRepository ticketRepository;

	@Transactional
	public FlightPlanCreateResponse createFlightPlan(Long airplaneId, FlightPlanCreateRequest flightPlanCreateRequest) {

		Airplane foundAirplane = airplaneRepository.findById(airplaneId)
			.orElseThrow(() -> new CustomException(AIRPLANE_NOT_FOUND));

		// todo 항공 스케쥴 검증 메서드 ( 구현 중 )
		// existsOverlappingSchedule(foundAirplane, flightPlanCreateRequest);

		FlightPlan newFlightPlan = FlightPlan.create(
			flightPlanCreateRequest.name(),
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


	public Page<FlightPlanGetListResponse> findFilteredFlightsPlanPage(
		FlightPlanGetRequest flightPlanGetRequest,
		Pageable pageable
	) {
		Page<FlightPlan> flightPlan = flightPlanRepository.findByFilters(
			flightPlanGetRequest.departure(),
			flightPlanGetRequest.arrival(),
			flightPlanGetRequest.boardingAt(),
			flightPlanGetRequest.landingAt(),
			pageable
		);
		return FlightPlanGetListResponse.from(flightPlan);
	}


	public List<FlightPlanGetResponse> findFlightPlan(Long flightPlanId) {
		return flightPlanRepository.findTicketInfoByFlightPlanId(flightPlanId);
	}


	@Transactional
	public FlightPlaneUpdateResponse updateFlightPlan(Long flightPlanId,
		FlightPlanUpdateRequest flightPlanUpdateRequest) {

		FlightPlan foundFlightPlan = flightPlanRepository.findById(flightPlanId)
			.orElseThrow(() -> new CustomException(FLIGHTPLAN_NOT_FOUND));

		foundFlightPlan.update(
			flightPlanUpdateRequest.departure(), flightPlanUpdateRequest.arrival(),
			flightPlanUpdateRequest.boardingAt(), flightPlanUpdateRequest.landingAt()
		);
		return FlightPlaneUpdateResponse.from(foundFlightPlan);
	}

	/**
	 * SIN WOO
	 * 사용되지 않는 메소드는 삭제 바랍니다.
	 */
	private void existsOverlappingSchedule(
		Airplane foundAirplane,
		FlightPlanCreateRequest flightPlanCreateRequest
	) {
	}

}
