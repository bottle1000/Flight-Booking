package flight_booking.demo.domain.payment.controller;

import com.fasterxml.jackson.databind.JsonNode;
import flight_booking.demo.domain.payment.dto.PaymentRes;
import flight_booking.demo.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

	private final PaymentService paymentService;

	@GetMapping("/{orderId}")
	public ResponseEntity<PaymentRes> findOrderForToss(@PathVariable Long orderId) {
		return new ResponseEntity<>(paymentService.findOrderUid(orderId), HttpStatus.OK);
	}

	@GetMapping("/success")
	public ResponseEntity<JsonNode> success(
		@RequestParam String paymentKey,
		@RequestParam String orderId,
		@RequestParam int amount
	) throws Exception {
		paymentService.verifyRequest(orderId, amount);
		return paymentService.confirm(paymentKey, orderId, amount);
	}

	// 사용자가 결제를 진행하는 중 오류 발생, 즉 프론트에서 결제가 실패해면 /fail URL로 리다이렉트
	// 하지만 여기서는 /fail을 백엔드로 리다이렉트
	@GetMapping("/fail")
	public ResponseEntity<String> fail(
		@RequestParam String code,
		@RequestParam String message,
		@RequestParam String orderId
	) {
		paymentService.userFail(orderId);
		return new ResponseEntity<>("주문번호 : " + orderId + "\n" +
			", 에러 메세지 : " + message +
			", 에러 코드 : " + code,
			HttpStatus.BAD_REQUEST);
	}
}
