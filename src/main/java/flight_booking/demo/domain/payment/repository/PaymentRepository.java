package flight_booking.demo.domain.payment.repository;

import flight_booking.demo.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByUid(String uid);
}
