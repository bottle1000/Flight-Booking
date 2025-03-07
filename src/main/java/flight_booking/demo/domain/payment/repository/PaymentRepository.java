package flight_booking.demo.domain.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import flight_booking.demo.domain.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentQueryRepository{
	Optional<Payment> findByUid(String uid);
}
