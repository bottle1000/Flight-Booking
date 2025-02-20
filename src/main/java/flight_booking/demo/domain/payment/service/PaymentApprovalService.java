package flight_booking.demo.domain.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PaymentApprovalService {

    /**
     * PaymentApprovalService : Toss Payment API 호출과 재시도 로직을 전담하는 클래스
     */

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${payment.toss.test_client_api_key}")
    private String secretKey;

    /**
     * Toss Payments의 결제 승인 API 호출
     * Spring Retry를 통해 재시도 로직 적용
     */
    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public JsonNode approvePayment(String paymentKey, String orderId, int amount) throws Exception {

        String url = "https://api.tosspayments.com/v1/payments/confirm";
        HttpHeaders headers = createAuthHeaders();

        JSONObject jsonObject = createApprovalRequestJson(paymentKey, orderId, amount);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonObject.toString(), headers);

        // UTF-8 인코딩을 보장하기 위해 메세지 컨버터 추가
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new CustomException(ResponseCode.PROVIDER_ERROR);
        }

        return objectMapper.readTree(responseEntity.getBody());
    }

    /**
     * Toss Payments 결제 최소 API 호출
     */
    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void cancelPayment(String paymentKey) throws Exception {
        String cancelUrl = "https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel";
        HttpHeaders headers = createAuthHeaders();

        JSONObject requestJson = new JSONObject();
        requestJson.put("cancelReason", "구매자가 취소를 원함");

        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson.toString(), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(cancelUrl, requestEntity, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new CustomException(ResponseCode.PROVIDER_ERROR);
        }
    }

    private HttpHeaders createAuthHeaders() {
        String formattedSecretKey = secretKey + ":";
        String encoded = Base64.getEncoder().encodeToString(formattedSecretKey.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encoded);
        return headers;
    }

    private JSONObject createApprovalRequestJson(String paymentKey, String orderId, int amount) {
        JSONObject requestJson = new JSONObject();
        requestJson.put("orderId", orderId);
        requestJson.put("amount", amount);
        requestJson.put("paymentKey", paymentKey);
        return requestJson;
    }
}
