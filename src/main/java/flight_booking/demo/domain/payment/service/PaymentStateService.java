package flight_booking.demo.domain.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.domain.airplane.entity.SeatState;
import flight_booking.demo.domain.invoice.entity.Invoice;
import flight_booking.demo.domain.invoice.repository.InvoiceRepository;
import flight_booking.demo.domain.order.entity.OrderState;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.payment.entity.PaymentState;
import flight_booking.demo.domain.payment.repository.PaymentRepository;
import flight_booking.demo.lock.Lock;
import flight_booking.demo.lock.repository.LockRepository;
import flight_booking.demo.s3.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentStateService {
    private final InvoiceRepository invoiceRepository;
    private final S3UploadService s3UploadService;
    private final PaymentRepository paymentRepository;
    private final RedissonClient redissonClient;

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


    @Transactional
    @Lock(key = "#payment.getOrder().getId()", prefix = "paymentLock:")
    public void cancelTimeOutPayments(Payment payment) {
        Payment paymentInDB = paymentRepository.findById(payment.getId())
                .orElseThrow();

        if (paymentInDB.getState() != PaymentState.IN_PROGRESS) {
            return;
        }

        payment.updatePaymentStatus(PaymentState.CANCEL);
        payment.getOrder().updateState(OrderState.CANCELED);
        payment.getOrder().getTicket().updateState(SeatState.IDLE);
    }
}
