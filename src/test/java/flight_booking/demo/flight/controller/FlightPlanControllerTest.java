package flight_booking.demo.flight.controller;

import static flight_booking.demo.flight.FlightPlanTestFixure.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


import com.fasterxml.jackson.databind.ObjectMapper;  // Jackson의 ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import flight_booking.demo.BaseTest;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.flight.repository.FlightPlanRepository;
import flight_booking.demo.domain.flight.repository.TicketRepository;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.user.entity.Role;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.flight.FlightPlanTestFixure;
import flight_booking.demo.security.jwt.TokenProvider;
import flight_booking.demo.security.utils.UserUtil;
import mockuser.WithMockUser;

@WithMockUser(role = Role.ADMIN)
@SpringBootTest
@Transactional
public class FlightPlanControllerTest extends BaseTest {

	@Autowired
	private MockMvc mockMvc;

	// 필드 선언
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private AirplaneRepository airplaneRepository;
	@Autowired
	private FlightPlanRepository flightPlanRepository;
	@Autowired
	private UserRepository userRepository;

	private Airplane airplane;
	private FlightPlan flightPlan;
	private Ticket ticket;
	private Order order;
	private TokenProvider tokenProvider;

	@Autowired
	private RestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		airplane = Airplane.from("test airplane");
		airplaneRepository.save(airplane);

		User userA = UserUtil.getCurrentUser();
		userA.setRole(Role.ADMIN);
		userRepository.save(userA);

		flightPlan = FlightPlan.create(
			"ICNNRT",
			Airport.ICN,
			Airport.NRT,
			10000,
			LocalDateTime.of(2050, 1, 1, 11, 11, 11).atZone(zoneId),
			LocalDateTime.of(2055, 1, 1, 11, 11, 11).atZone(zoneId),
			airplane);
		flightPlanRepository.save(flightPlan);

		Ticket ticket = new Ticket("1A", flightPlan);
		ticketRepository.save(ticket);

		// LocalDateTime 직렬화 및 역직렬화 로직
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	@Test
	@DisplayName("항공권 일정 생성 통합 테스트")
	public void createFlightPlan() throws Exception {

		FlightPlanCreateRequest request = FlightPlanTestFixure.createFlightPlanRequest();
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/airplanes/1/flight-plans")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request))
		).andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(request.name()))
			// .andExpect(jsonPath("$.departure").value(request.departure()))
			// .andExpect(jsonPath("$.arrival").value(request.arrival()))
			.andExpect(jsonPath("$.boardingAt").value(request.boardingAt()))
			.andExpect(jsonPath("$.landingAt").value(request.landingAt()))
			.andExpect(jsonPath("$.price").value(request.price()));
	}


	@Test
	@DisplayName("항공권 일정 검색 통합 테스트")
	void findFlightPlanPage() {
		//given
		String departure = "ICN";
		String arrival = "GMP";
		LocalDate fromDate = LocalDate.of(2025, 3, 1);
		LocalDate toDate = LocalDate.of(2025, 3, 15);
		int page = 0;
		int size = 10;

		//when
		String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/flight-plans")
			.queryParam("departure", departure)
			.queryParam("arrival", arrival)
			.queryParam("fromDate", fromDate)
			.queryParam("toDate", toDate)
			.toUriString();

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		//then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("content"));
	}

	@Test
	void findFlightPlan() {

	}

	@Test
	void updateFlightPlan() {
	}

}