package flight_booking.demo.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flight_booking.demo.BaseTest;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.entity.SeatState;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.flight.repository.FlightPlanRepository;
import flight_booking.demo.domain.flight.repository.TicketRepository;
import flight_booking.demo.domain.order.dto.request.OrderCreateRequestDto;
import flight_booking.demo.domain.order.dto.request.OrderUpdateRequestDto;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.entity.OrderState;
import flight_booking.demo.domain.order.repository.OrderRepository;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.utils.UserUtil;
import mockuser.WithMockUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@SpringBootTest
@Transactional
class OrderControllerTest extends BaseTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AirplaneRepository airplaneRepository;
    @Autowired
    private FlightPlanRepository flightPlanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private FlightPlan flightPlan;
    private Ticket ticket;
    private Ticket ticketForChange;
    private Ticket ticketForCreate;
    private Order order;
    private Discount discount;

    @BeforeEach
    void setUp() {
        Airplane airplane = Airplane.from("test airplane");
        airplaneRepository.save(airplane);

        discount = new Discount(
                DiscountType.BASIC,
                0,
                5000,
                "DISCOUNT BY GRADE",
                ZonedDateTime.of(
                        2050, 3, 1, 1, 1, 1, 1, ZoneId.of("Asia/Seoul")
                ),
                ZonedDateTime.of(
                        2051, 3, 1, 1, 1, 1, 1, ZoneId.of("Asia/Seoul")
                )

        );
        discountRepository.save(discount);

        User userA = UserUtil.getCurrentUser();
        userRepository.save(userA);

        flightPlan = FlightPlan.create(
                "일본 나리타행 새벽발",
                Airport.ICN,
                Airport.NRT,
                10000,
                ZonedDateTime.of(2050, 3, 1, 1, 1, 1, 1, ZoneId.of("Asia/Seoul")),
                ZonedDateTime.of(2051, 3, 1, 1, 1, 1, 1, ZoneId.of("Asia/Seoul")),
                airplane);
        flightPlanRepository.save(flightPlan);

        ticket = new Ticket("1A", flightPlan);
        ticketRepository.save(ticket);
        ticketForCreate = new Ticket("2A", flightPlan);
        ticketRepository.save(ticketForCreate);
        ticketForChange = new Ticket("3A", flightPlan);
        ticketRepository.save(ticketForChange);

        order = new Order(UserUtil.getCurrentUser(), ticket, flightPlan.getPrice());
        ticket.updateState(SeatState.BOOKED);
        orderRepository.save(order);
    }

    @Test
    void createOrder() throws Exception {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto(
                ticketForCreate.getId(),
                List.of(discount.getId()));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderState").value(OrderState.NOT_PAID.toString()))
                .andExpect(jsonPath("$.ticketId").value(ticketForCreate.getId()))
                .andExpect(jsonPath("$.price").value(flightPlan.getPrice() - discount.getAmount()))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response: " + responseBody);
    }

    @Test
    void findOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/" + order.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.orderState").value(order.getState().toString()))
                .andExpect(jsonPath("$.ticketId").value(order.getTicket().getId()))
                .andExpect(jsonPath("$.price").value(order.getPrice()));
    }

    @Test
    void updateOrder() throws Exception {
        OrderUpdateRequestDto requestDto = new OrderUpdateRequestDto(ticketForChange.getId());
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.orderState").value(order.getState().toString()))
                .andExpect(jsonPath("$.ticketId").value(ticketForChange.getId()))
                .andExpect(jsonPath("$.price").value(order.getPrice()));
    }

    @Test
    void containerTestWorking() {
        Assertions.assertTrue(true);
    }
}