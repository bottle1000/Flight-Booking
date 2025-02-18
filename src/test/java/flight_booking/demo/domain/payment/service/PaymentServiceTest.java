package flight_booking.demo.domain.payment.service;

import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.payment.entity.PaymentState;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.mockuser.WithMockUser;
import flight_booking.demo.security.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@WithMockUser // 설정 하려면 추가 가능()
class PaymentServiceTest {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        User user = userRepository.save(UserUtil.getCurrentUser());

    }


    @Test
    public void 결제_요청_검증_테스트() {
        //given
        User user = UserUtil.getCurrentUser();
        String orderId = "AFKD859KD";
        int amount = 10000;
        Payment payment = new Payment(1, null, PaymentState.IN_PROGRESS, orderId, "뱅기티켓", amount, null);

        //when
        Mockito.when(paymentRepository.findByUid(orderId)).thenReturn(Optional.of(payment));

        //then
        assertDoesNotThrow(() -> paymentService.verifyRequest(orderId, amount));

    }
}