package flight_booking.demo.order.controller;

import flight_booking.demo.BaseTest;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.flight.repository.TicketRepository;
import mockuser.WithMockUser;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.repository.FlightPlanRepository;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.repository.OrderRepository;
import flight_booking.demo.domain.order.service.OrderService;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.utils.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@WithMockUser
@SpringBootTest
@Transactional
class OrderControllerTest extends BaseTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DiscountRepository discountRepository;
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


    @BeforeEach
    void setUp() {
        airplane = Airplane.from("test airplane");
        airplaneRepository.save(airplane);

        User userA = UserUtil.getCurrentUser();
        userRepository.save(userA);

        flightPlan = FlightPlan.create(
                "일본 나리타행 새벽발",
                Airport.ICN,
                Airport.NRT,
                10000,
                LocalDateTime.of(2026, 1,1,1,1),
                LocalDateTime.of(2026, 1,1,1,1),
                airplane);
        flightPlanRepository.save(flightPlan);

        Ticket ticket = new Ticket("1A", flightPlan);
        ticketRepository.save(ticket);

        order = new Order(UserUtil.getCurrentUser(), ticket, flightPlan.getPrice());
        orderRepository.save(order);
    }

    @Test
    void containerTestWorking() {
        Assertions.assertTrue(true);
    }
}