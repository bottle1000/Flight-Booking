package flight_booking.demo.domain.receipt.repository;

import flight_booking.demo.domain.receipt.entity.ReceiptDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptDiscountRepository extends JpaRepository<ReceiptDiscount, Integer> {
}
