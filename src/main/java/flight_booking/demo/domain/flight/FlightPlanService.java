package flight_booking.demo.domain.flight;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.entity.SeatColumn;
import flight_booking.demo.domain.airplane.entity.Ticket;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.airplane.repository.TicketRepository;
import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.dto.request.FlightPlanUpdateRequest;
import flight_booking.demo.domain.flight.dto.response.FlightPlanCreateResponse;
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

	public Page<FlightPlanGetResponse> getFilteredFlightsPlanList(String departure, String arrival,
		LocalDateTime boardingAt,
		LocalDateTime landingAt,
		Pageable pageable
	) {
		Page<FlightPlan> flightPlan = flightPlanRepository.findByFilters(departure, arrival, boardingAt, landingAt,
			pageable);
		return FlightPlanGetResponse.from(flightPlan);
	}

	@Transactional
	public FlightPlanCreateResponse createFlightPlan(FlightPlanCreateRequest flightPlanCreateRequest) {
		//todo 예외 처리해야 함.
		Airplane foundAirplane = airplaneRepository.findById(flightPlanCreateRequest.airplaneId()).orElseThrow();

		FlightPlan newFlightPlan = FlightPlan.create(
			flightPlanCreateRequest.departure(),
			flightPlanCreateRequest.arrival(),
			flightPlanCreateRequest.price(),
			flightPlanCreateRequest.boardingAt(),
			flightPlanCreateRequest.landingAt(),
			foundAirplane
		);

		// todo db 비용이 너무 많다... 개선 필요
		for (int row = 1; row <= 10; row++) {
			for (SeatColumn column : SeatColumn.values()) {
				Ticket ticket = new Ticket(row + column.name(), newFlightPlan);
				ticketRepository.save(ticket);
			}
		}
		FlightPlan savedFlightPlan = flightPlanRepository.save(newFlightPlan);
		return FlightPlanCreateResponse.from(savedFlightPlan);
	}

	public FlightPlaneUpdateResponse updateFlightPlan(Long id, FlightPlanUpdateRequest flightPlanUpdateRequest) {
		FlightPlan foundFlightPlan = flightPlanRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("비행 스케쥴이 존재하지 않습니다: " + id));
		foundFlightPlan.update(
			flightPlanUpdateRequest.departure(), flightPlanUpdateRequest.arrival(),
			flightPlanUpdateRequest.boardingAt(), flightPlanUpdateRequest.landingAt()
		);
		return FlightPlaneUpdateResponse.from(foundFlightPlan);
	}
}
