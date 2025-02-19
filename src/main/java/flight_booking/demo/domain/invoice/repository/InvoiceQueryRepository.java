package flight_booking.demo.domain.invoice.repository;

public interface InvoiceQueryRepository {

    String findKeyByPaymentId(Long id);
}
