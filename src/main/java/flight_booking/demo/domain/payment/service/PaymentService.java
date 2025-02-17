package flight_booking.demo.domain.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import flight_booking.demo.common.entity.exception.CustomException;
import flight_booking.demo.common.entity.exception.ResponseCode;
import flight_booking.demo.domain.invoice.entity.Invoice;
import flight_booking.demo.domain.invoice.repository.InvoiceRepository;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;
    private final InvoiceRepository invoiceRepository;

    @Value("${payment.toss.test_secret_api_key}")
    private String secretKey;

    /**
     * 쿼리 파라미터의 amount 값과 setAmount()의 amount 파라미터의 값이 같은지 반드시 확인하세요.
     * 클라이언트에서 결제 금액을 조작하는 행위를 방지할 수 있습니다.
     * 만약 값이 다르다면 결제를 취소하고 구매자에게 알려주세요.
     */
    @Transactional
    public void verifyRequest(String orderId, int amount) {
        Payment payment = paymentRepository.findByUid(orderId)
                .orElseThrow();
        if (payment.getAmount() != amount) {
            throw new CustomException(ResponseCode.PAYMENT_AMOUNT_MISMATCH);
        }
    }

    @Transactional
    public ResponseEntity<JsonNode> confirm(String paymentKey, String orderId, int amount) throws Exception {

        // Toss Payments 최종 결제 승인 API URL
        String url = "https://api.tosspayments.com/v1/payments/confirm";

        secretKey = secretKey + ":"; // : 이거 붙여줘야함..
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedKey);

        //요청에 보낼 JSON
        JSONObject requestJson = new JSONObject();
        requestJson.put("orderId", orderId);
        requestJson.put("amount", amount);
        requestJson.put("paymentKey", paymentKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson.toString(), headers);

        //Post 요청 보내기
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        Payment payment = paymentRepository.findByUid(orderId)
                .orElseThrow(() -> new CustomException(ResponseCode.ORDER_UUID_NOT_FOUND));

        // 결제 성공 및 실패 비즈니스 로직을 구현.
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

            payment.getOrder().updateOrderStatus("PAID"); //오더 상태 업데이트
            payment.updatePaymentStatus("COMPLETE");

            //최종 승인 났으면 결제 정보 DB에 저장하기
            invoiceRepository.save(new Invoice(jsonNode));

            return ResponseEntity.status(responseEntity.getStatusCode()).body(jsonNode);
        } else {
            payment.getOrder().updateOrderStatus("CANCELED");
            payment.updatePaymentStatus("FAIL");
            //TODO: 토스 예외처리 만들기
            throw new IllegalStateException("토스 페이 최종 결제 실패 : " + responseEntity.getStatusCode());
        }
    }

    @Transactional
    public void userFail(String orderId) {
        Payment payment = paymentRepository.findByUid(orderId)
                .orElseThrow(() -> new CustomException(ResponseCode.ORDER_UUID_NOT_FOUND));
        payment.getOrder().updateOrderStatus("CANCELED");
        payment.updatePaymentStatus("FAIL");
    }
}
