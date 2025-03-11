package flight_booking.demo.domain.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ServerErrorResponseCode;
import flight_booking.demo.common.exception.payment.PaymentErrorResponseCode;
import flight_booking.demo.common.exception.payment.PaymentException;
import flight_booking.demo.common.exception.payment.client.ClientPaymentErrorResponseCode;
import flight_booking.demo.common.exception.payment.client.ClientPaymentException;
import flight_booking.demo.domain.invoice.entity.Invoice;
import flight_booking.demo.domain.invoice.repository.InvoiceRepository;
import flight_booking.demo.domain.order.entity.OrderState;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.payment.entity.PaymentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentApprovalService {

    /**
     * PaymentApprovalService : Toss Payment API 호출과 재시도 로직을 전담하는 클래스
     */

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${payment.toss.test_secret_api_key}")
    private String secretKey;

    /**
     * Toss Payments의 결제 승인 API 호출
     * Spring Retry를 통해 재시도 로직 적용
     */
    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2),
            retryFor = { PaymentException.class, CustomException.class },
            noRetryFor = ClientPaymentException.class
    )
    public JsonNode approvePayment(String paymentKey, String orderId, int amount) throws Exception {
        String url = "https://api.tosspayments.com/v1/payments/confirm";
        JSONObject jsonObject = createApprovalRequestJson(paymentKey, orderId, amount);

        try {
            HttpResponse<String> response = executePost(url, jsonObject.toString());
            log.info("Payment executed successfully: {}", response.body());

            JsonNode responseJson = objectMapper.readTree(response.body());
            handlingPaymentError(responseJson);
            return responseJson;
        } catch (HttpStatusCodeException | ResourceAccessException e) {
            throw new CustomException(ServerErrorResponseCode.NETWORK_ERROR);
        } catch (Exception e) {
            log.error("Payment failed: {}\n Error Trace: {}", e.getMessage(), e.getStackTrace());
            throw e;
        }
    }

    /**
     * Toss Payments 결제 최소 API 호출
     */
    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2),
            retryFor = {PaymentException.class, CustomException.class},
            noRetryFor = ClientPaymentException.class
    )
    public void cancelPayment(String paymentKey) throws Exception {
        log.info("=====================결제 취소===================== ");
        String cancelUrl = "https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel";

        JSONObject requestJson = new JSONObject();
        requestJson.put("cancelReason", "구매자가 취소를 원함");

        try {
            HttpResponse<String> response = executePost(cancelUrl, requestJson.toString());
            JsonNode responseJson = objectMapper.readTree(response.body());
            handlingPaymentError(responseJson);
        } catch (HttpStatusCodeException | ResourceAccessException e) {
            throw new CustomException(ServerErrorResponseCode.NETWORK_ERROR);
        }
    }


    private JSONObject createApprovalRequestJson(String paymentKey, String orderId, int amount) {
        JSONObject requestJson = new JSONObject();
        requestJson.put("orderId", orderId);
        requestJson.put("amount", amount);
        requestJson.put("paymentKey", paymentKey);
        return requestJson;
    }

    /**
     * HttpClient를 이용한 POST 요청 실행 (헤더와 본문 적용)
     */
    private HttpResponse<String> executePost(String url, String requestBody)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8)))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private void handlingPaymentError(JsonNode responseJson){
        String errorCodeString = responseJson.get("code").asText("");
        String errorMessage = responseJson.get("message").asText("");

        Optional<ClientPaymentErrorResponseCode> clientEx = ClientPaymentErrorResponseCode.has(errorCodeString);
        if (!errorCodeString.isBlank()) {
            if (clientEx.isPresent()) {
                throw new ClientPaymentException(clientEx.get());
            } else {
                PaymentErrorResponseCode paymentFailed = PaymentErrorResponseCode.setPaymentFailedMessage(errorMessage);
                throw new PaymentException(paymentFailed);
            }
        }
    }
}
