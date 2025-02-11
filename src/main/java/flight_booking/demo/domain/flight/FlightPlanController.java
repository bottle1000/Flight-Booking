package flight_booking.demo.domain.flight;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.National;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/flightplans")
@RequiredArgsConstructor
public class FlightPlanController {

	private final FlightPlanService flightPlanService;

	// 항공권 검색
	@GetMapping()
	public ResponseEntity<Page<FlightPlan>> getFlightPlanList(
		@RequestParam(required = false) National from,
		@RequestParam(required = false) National to,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate boardingAt,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate landingAt,
		@PageableDefault Pageable pageable
	) {
		Page<FlightPlan> results = flightPlanService.getFilteredFlightsPlanList(from, to, boardingAt, landingAt, pageable);
		return ResponseEntity.ok().body(results);
	}
}
