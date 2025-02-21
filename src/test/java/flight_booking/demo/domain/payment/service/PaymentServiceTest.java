package flight_booking.demo.domain.payment.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import flight_booking.demo.BaseTest;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ResponseCode;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.flight.repository.FlightPlanRepository;
import flight_booking.demo.domain.flight.repository.TicketRepository;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.repository.OrderRepository;
import flight_booking.demo.domain.order.service.OrderService;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.payment.repository.PaymentRepository;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.utils.UserUtil;
import mockuser.WithMockUser;

@SpringBootTest
@WithMockUser // 설정 하려면 추가 가능()
@Transactional
class PaymentServiceTest extends BaseTest {

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
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private PaymentRepository paymentRepository;


	private Airplane airplane;
	private FlightPlan flightPlan;
	private Ticket ticket;
	private Order order;
	private Payment payment;

	@BeforeEach
	public void setUp() {
		airplane = Airplane.from("test airplane");
		airplaneRepository.save(airplane);

		User userA = UserUtil.getCurrentUser();
		userRepository.save(userA);

		flightPlan = FlightPlan.create(
			"나리타행 새벽 비행기",
			Airport.ICN,
			Airport.NRT,
			10000,
			ZonedDateTime.of(2050, 3, 1, 1, 1, 1, 1, ZoneId.of("Asia/Seoul")),
			ZonedDateTime.of(2051, 3, 1, 1, 1, 1, 1, ZoneId.of("Asia/Seoul")),
			airplane);
		flightPlanRepository.save(flightPlan);

		ticket = new Ticket("1A", flightPlan);
		ticketRepository.save(ticket);

		order = new Order(UserUtil.getCurrentUser(), ticket, flightPlan.getPrice());
		orderRepository.save(order);

		payment = order.getPayment();
		paymentRepository.save(payment);
	}

	@Test
	void verifyRequest_성공_테스트() {
		// given
		String orderId = payment.getUid();
		int amount = payment.getAmount();

		// when & then
		assertDoesNotThrow(() -> paymentService.verifyRequest(orderId, amount));
	}

	@Test
	void verifyRequest_금액_불일치_예외_테스트() {
		// given
		String orderId = payment.getUid();
		int incorrectAmount = payment.getAmount() + 1000;

		// when & then
		CustomException exception = assertThrows(CustomException.class,
			() -> paymentService.verifyRequest(orderId, incorrectAmount));

		assertEquals(ResponseCode.PAYMENT_AMOUNT_MISMATCH, exception.getResponseCode());
	}
}