package flight_booking.demo.domain.payment.repository;

import flight_booking.demo.domain.payment.entity.Payment;
import java.util.List;

public interface PaymentQueryRepository {

    List<Payment> findAllExpired();
}
