package flight_booking.demo.flight.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import flight_booking.demo.BaseTest;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.flight.dto.request.FlightPlanCreateRequest;
import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.flight.repository.FlightPlanRepository;
import flight_booking.demo.domain.flight.repository.TicketRepository;
import flight_booking.demo.domain.user.entity.Role;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.flight.FlightPlanTestFixure;
import flight_booking.demo.security.utils.UserUtil;
import mockuser.WithMockUser;

@WithMockUser(role = Role.ADMIN)
@SpringBootTest
@Transactional
public class FlightPlanControllerTest extends BaseTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
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

	@BeforeEach
	void setUp() {
		airplane = Airplane.from("test airplane");
		airplane = airplaneRepository.save(airplane);

		User userA = UserUtil.getCurrentUser();
		userA.setRole(Role.ADMIN);
		userRepository.save(userA);

		flightPlan = FlightPlan.create(
			"ICNGMP2999",
			Airport.ICN,
			Airport.NRT,
			10000,
			ZonedDateTime.of(
				2050, 3, 1, 1, 1, 1, 1, ZoneId.of("Asia/Seoul")
			),
			ZonedDateTime.of(
				2051, 3, 1, 1, 1, 1, 1, ZoneId.of("Asia/Seoul")
			),
			airplane);
		flightPlanRepository.save(flightPlan);

		Ticket ticket = new Ticket("1A", flightPlan);
		ticketRepository.save(ticket);
	}

	@Test
	@DisplayName("항공권 일정 생성 통합 테스트 - 성공")
	public void createFlightPlan() throws Exception {

		Long airplaneId = airplane.getId();
		FlightPlanCreateRequest request = FlightPlanTestFixure.createFlightPlanRequest();
		String requestJson = objectMapper.writeValueAsString(request);
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/admin/airplanes/" + airplaneId + "/flight-plans")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestJson)
			).andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(request.name()))
			.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		System.out.println("Response: " + responseBody);
	}

	@Test
	@DisplayName("항공권 일정 검색 통합 테스트 - 성공")
	public void findFlightPlanPage() throws Exception {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String expectedBoardingAt = flightPlan.getBoardingAt().format(formatter);
		String expectedLandingAt = flightPlan.getLandingAt().format(formatter);

		MvcResult result = mockMvc.perform(get("/flight-plans")
				.param("departure", "ICN")
				.param("arrival", "NRT")
				.param("boardingAt", expectedBoardingAt)
				.param("landingAt", expectedLandingAt))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].departure").value("ICN"))
			.andExpect(jsonPath("$.content[0].arrival").value("NRT"))
			.andExpect(jsonPath("$.content[0].boardingAt").value("2050-03-01T01:01:01"))
			.andExpect(jsonPath("$.content[0].landingAt").value("2051-03-01T01:01:01"))
			.andExpect(jsonPath("$.totalCount").value(1))
			.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		System.out.println("Response: " + responseBody);
	}

	@Test
	@DisplayName("항공권 일정 단건 조회 통합 테스트 - 성공")
	public void findFlightPlan() throws Exception {
		Long flightPlanId = flightPlan.getId();
		MvcResult result = mockMvc.perform(get("/flight-plans/" + flightPlanId))
			.andExpect(status().isOk())
			// 티켓 안의 flightPlan 정보 검증
			.andExpect(jsonPath("$[0].ticketList[0].flightPlan.name").value(flightPlan.getName()))
			.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		System.out.println("Response: " + responseBody);
	}

}