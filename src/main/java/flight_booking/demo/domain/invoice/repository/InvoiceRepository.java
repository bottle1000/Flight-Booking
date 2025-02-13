package flight_booking.demo.domain.receipt.repository;

import flight_booking.demo.domain.receipt.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}