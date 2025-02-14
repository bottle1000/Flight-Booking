package flight_booking.demo.domain.flight;

import static flight_booking.demo.common.entity.exception.ResponseCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flight_booking.demo.common.entity.exception.CustomException;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.airplane.repository.TicketRepository;
import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.dto.request.FlightPlanGetRequest;
import flight_booking.demo.domain.flight.dto.request.FlightPlanUpdateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanGetResponse;
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

	public Page<FlightPlanGetResponse> findFilteredFlightsPlanPage(
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
		return FlightPlanGetResponse.from(flightPlan);
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

	private void existsOverlappingSchedule(
		Airplane foundAirplane,
		FlightPlanCreateRequest flightPlanCreateRequest
	) {
	}
}
