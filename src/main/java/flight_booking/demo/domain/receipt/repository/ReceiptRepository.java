package flight_booking.demo.domain.receipt.repository;

import flight_booking.demo.domain.receipt.entity.Receipt;
import flight_booking.demo.domain.receipt.entity.ReceiptDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    ReceiptDiscount save(ReceiptDiscount receiptDiscount);
}