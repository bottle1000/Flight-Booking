package flight_booking.demo.domain.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import flight_booking.demo.common.event.PaymentRefundEvent;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ServerErrorResponseCode;
import flight_booking.demo.common.exception.payment.PaymentErrorResponseCode;
import flight_booking.demo.common.exception.payment.PaymentException;
import flight_booking.demo.common.exception.payment.client.ClientPaymentException;
import flight_booking.demo.domain.invoice.repository.InvoiceRepository;
import flight_booking.demo.domain.order.entity.OrderState;
import flight_booking.demo.domain.payment.dto.PaymentRes;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.payment.entity.PaymentState;
import flight_booking.demo.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static flight_booking.demo.common.exception.ServerErrorResponseCode.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentApprovalService paymentApprovalService;
    private final InvoiceRepository invoiceRepository;

    /**
     * 쿼리 파라미터의 amount 값과 setAmount()의 amount 파라미터의 값이 같은지 반드시 확인하세요.
     * 클라이언트에서 결제 금액을 조작하는 행위를 방지할 수 있습니다.
     * 만약 값이 다르다면 결제를 취소하고 구매자에게 알려주세요.
     */
    public void verifyRequest(String orderId, int amount) {
        Payment payment = paymentRepository.findByUid(orderId)
                .orElseThrow(() -> new CustomException(ServerErrorResponseCode.ORDER_UUID_NOT_FOUND));
        if (payment.getAmount() != amount) {
            throw new PaymentException(PaymentErrorResponseCode.PAYMENT_AMOUNT_MISMATCH);
        }
    }

    public ResponseEntity<JsonNode> confirm(String paymentKey, String orderId, int amount) {
        verifyRequest(orderId, amount);

        Payment payment = paymentRepository.findByUid(orderId)
                .orElseThrow(() -> new CustomException(ServerErrorResponseCode.ORDER_UUID_NOT_FOUND));

        try {
            JsonNode approvedPayment = paymentApprovalService.approvePayment(paymentKey, orderId, amount);
            paymentApprovalService.processPayment(payment, approvedPayment);

            return ResponseEntity.ok(approvedPayment);
        } catch (ClientPaymentException | PaymentException e) {
            paymentApprovalService.cancelPayment(payment);
            throw e;
        } catch (Exception e) {
            paymentApprovalService.cancelPayment(payment);
            throw new CustomException(INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * 사용자가 결제 도중 실패했을 때 처리
     */
    @Transactional
    public void userFail(String orderId) {
        Payment payment = paymentRepository.findByUid(orderId)
                .orElseThrow(() -> new CustomException(ServerErrorResponseCode.ORDER_UUID_NOT_FOUND));
        payment.getOrder().updateState(OrderState.CANCELED);
        payment.updatePaymentStatus(PaymentState.FAIL);
    }

    public PaymentRes findOrderUid(Long orderId) {
        Payment payment = paymentRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ServerErrorResponseCode.ORDER_ID_NOT_FOUND));
        return new PaymentRes(payment.getUid(), payment.getAmount(), payment.getName());
    }

    /**
     * 주문 취소 이벤트를 처리
     * 결제 취소 API 호출
     */
    @TransactionalEventListener
    public void handleOrderCancelEvent(PaymentRefundEvent event) throws Exception {
        Payment payment = paymentRepository.findByUid(event.getOrderId())
                .orElseThrow();

        String paymentKey = invoiceRepository.findKeyByPaymentId(payment.getId());
        paymentApprovalService.cancelPayment(paymentKey);

        payment.updatePaymentStatus(PaymentState.CANCEL);
        payment.getOrder().updateState(OrderState.CANCELED);
    }
}
