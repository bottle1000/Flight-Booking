package flight_booking.demo.domain.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.domain.invoice.entity.Invoice;
import flight_booking.demo.domain.invoice.repository.InvoiceRepository;
import flight_booking.demo.domain.order.entity.OrderState;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.payment.entity.PaymentState;
import flight_booking.demo.s3.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentStateService {
    private final InvoiceRepository invoiceRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public void processPayment(Payment payment, JsonNode tossDto) {
        try {
            payment.updatePaymentStatus(PaymentState.COMPLETE);
            payment.getOrder().updateState(OrderState.PAID);
            invoiceRepository.save(createInvoice(tossDto, payment));
            s3UploadService.upload(payment, tossDto.toString());
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void cancelPayment(Payment payment) {
        payment.getOrder().updateState(OrderState.CANCELED);
        payment.updatePaymentStatus(PaymentState.FAIL);
    }

    private Invoice createInvoice(JsonNode tossDto, Payment payment) {
        return new Invoice(tossDto, payment);
    }
}
