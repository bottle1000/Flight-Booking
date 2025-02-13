package flight_booking.demo.domain.payment.repository;

import flight_booking.demo.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
