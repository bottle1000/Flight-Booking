package flight_booking.demo.domain.invoice.repository;

import flight_booking.demo.domain.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}