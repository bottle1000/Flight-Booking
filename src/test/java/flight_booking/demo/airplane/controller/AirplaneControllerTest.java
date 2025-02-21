package flight_booking.demo.airplane.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import flight_booking.demo.BaseTest;
import flight_booking.demo.domain.airplane.dto.request.AirplaneCreateRequest;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.user.entity.Role;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.utils.UserUtil;
import mockuser.WithMockUser;

@WithMockUser(role = Role.ADMIN)
@SpringBootTest
@Transactional
public class AirplaneControllerTest extends BaseTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private AirplaneRepository airplaneRepository;
	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {

		User userA = UserUtil.getCurrentUser();
		userA.setRole(Role.ADMIN);
		userRepository.save(userA);

		Airplane airplane = Airplane.from("airplane test");
		airplaneRepository.save(airplane);
	}

	@Test
	@DisplayName("항공기 생성 통합 테스트 - 성공")
	public void createAirplane() throws Exception {
		AirplaneCreateRequest request = new AirplaneCreateRequest(
			"항공기10"
		);
		String requestJson = objectMapper.writeValueAsString(request);

		mockMvc.perform(MockMvcRequestBuilders.post("/admin/airplanes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
			).andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(request.name()));
	}

	@Test
	@DisplayName("항공기 검색 통합 테스트 - 성공")
	public void findAirplaneList() throws Exception {
		mockMvc.perform(get("/admin/airplanes"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].name").value("airplane test"));

	}
}
