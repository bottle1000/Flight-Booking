package flight_booking.demo.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import flight_booking.demo.BaseTest;
import flight_booking.demo.common.exception.payment.PaymentErrorResponseCode;
import flight_booking.demo.common.exception.payment.PaymentException;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.repository.AirplaneRepository;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.flight.repository.FlightPlanRepository;
import flight_booking.demo.domain.flight.repository.TicketRepository;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.entity.OrderState;
import flight_booking.demo.domain.order.repository.OrderRepository;
import flight_booking.demo.domain.order.service.OrderService;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.payment.entity.PaymentState;
import flight_booking.demo.domain.payment.repository.PaymentRepository;
import flight_booking.demo.domain.payment.service.PaymentApprovalService;
import flight_booking.demo.domain.payment.service.PaymentService;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.utils.UserUtil;
import mockuser.WithMockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WithMockUser // 설정 하려면 추가 가능()
@Transactional
@ExtendWith(MockitoExtension.class)
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
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentService paymentService;
    @MockitoSpyBean
    private PaymentApprovalService paymentApprovalService;

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
        PaymentException exception = assertThrows(PaymentException.class,
                () -> paymentService.verifyRequest(orderId, incorrectAmount));

        assertEquals(PaymentErrorResponseCode.PAYMENT_AMOUNT_MISMATCH, exception.getCode());
    }

    @Test
    void 결제_승인시_결제와_주문_상태_업데이트() throws Exception {
        //given
        String paymentKey = "tgen_20250220154039uPz26";
        String orderUid = payment.getUid();
        int price = order.getPrice();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = "{\n" +
                "  \"mId\": \"tgen_docs\",\n" +
                "  \"lastTransactionKey\": \"txrd_a01jmh1e8sgq0mwjggh8xtty32f\",\n" +
                "  \"paymentKey\": \"tgen_20250220154039uPz26\",\n" +
                "  \"orderId\": \""+orderUid+"\",\n" +
                "  \"orderName\": \"아 일본여행마렵네1A\",\n" +
                "  \"status\": \"DONE\",\n" +
                "  \"secret\": \"ps_GjLJoQ1aVZqeOMvpG0yA3w6KYe2R\",\n" +
                "  \"easyPay\": {\n" +
                "    \"provider\": \"토스페이\",\n" +
                "    \"amount\": "+ price + ",\n" +
                "    \"discountAmount\": 0\n" +
                "  },\n" +
                "  \"currency\": \"KRW\",\n" +
                "  \"totalAmount\": 1000000,\n" +
                "  \"metadata\": null\n" +
                "}";

        JsonNode approvedPayment = objectMapper.readTree(json);

        Mockito.doReturn(approvedPayment)
                .when(paymentApprovalService)
                .approvePayment(paymentKey, orderUid, price);

        // When
        paymentService.verifyRequest(orderUid, price);
        ResponseEntity<JsonNode> response = paymentService.confirm(paymentKey, orderUid, price);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(approvedPayment, response.getBody());
        assertEquals(OrderState.PAID, order.getState());
        assertEquals(PaymentState.COMPLETE, payment.getState());
    }


    @Test
    void 결제_실패시_결제와_주문_상태_업데이트() throws Exception {
        //given
        String paymentKey = "tgen_20250220154039uPz26";
        String orderUid = payment.getUid();
        int price = order.getPrice();

        Mockito.doThrow(new RuntimeException("HTTP 400~500번대 오류 발생"))
                .when(paymentApprovalService)
                .approvePayment(paymentKey, orderUid, price);

        //when & then
        assertThrows(RuntimeException.class, () -> {
            paymentService.confirm(paymentKey, orderUid, price);
        });

        assertEquals(OrderState.CANCELED, order.getState());
        assertEquals(PaymentState.FAIL, payment.getState());
    }


}