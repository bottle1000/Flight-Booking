package flight_booking.demo.domain.payment.repository;

import flight_booking.demo.domain.payment.dto.PaymentQueryDto;
import flight_booking.demo.domain.payment.entity.PaymentState;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentQueryRepository {

    List<PaymentQueryDto> findByStateAndCreatedAtBefore(PaymentState paymentState, LocalDateTime tenMinutesAge);
}
