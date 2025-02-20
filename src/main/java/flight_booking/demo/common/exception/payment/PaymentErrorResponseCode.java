package flight_booking.demo.common.exception.payment;

import flight_booking.demo.common.exception.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
public enum PaymentErrorResponseCode {
    //  Toss 결제 관련
    PAYMENT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "결제 처리 중 오류가 발생했습니다."),
    PAYMENT_VERIFICATION_FAILED(HttpStatus.PAYMENT_REQUIRED, "결제 검증에 실패했습니다."),
    REFUND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "환불 처리 중 오류가 발생했습니다."),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 정보를 찾을 수 없습니다."),
    PAYMENT_AMOUNT_MISMATCH(HttpStatus.PAYMENT_REQUIRED, "결제 금액 불일치: 요청된 금액과 저장된 금액이 다릅니다."),

    // HttpStatus.BAD_REQUEST
    CARD_PROCESSING_ERROR(HttpStatus.BAD_REQUEST, "카드사에서 오류가 발생했습니다."),
    PROVIDER_ERROR(HttpStatus.BAD_REQUEST, "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    UNAPPROVED_ORDER_ID(HttpStatus.BAD_REQUEST, "아직 승인되지 않은 주문번호입니다."),

    // HttpStatus.FORBIDDEN
    EXCEED_MAX_AUTH_COUNT(HttpStatus.FORBIDDEN, "최대 인증 횟수를 초과했습니다. 카드사로 문의해주세요."),

    // 500 Internal Server Error
    FAILED_PAYMENT_INTERNAL_SYSTEM_PROCESSING(HttpStatus.INTERNAL_SERVER_ERROR, "결제가 완료되지 않았어요. 다시 시도해주세요."),
    FAILED_INTERNAL_SYSTEM_PROCESSING(HttpStatus.INTERNAL_SERVER_ERROR, "내부 시스템 처리 작업이 실패했습니다. 잠시 후 다시 시도해주세요."),
    UNKNOWN_PAYMENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "결제에 실패했어요. 같은 문제가 반복된다면 은행이나 카드사로 문의해주세요.");

    private final HttpStatus status;
    private String message;
    private final ErrorType type;

    PaymentErrorResponseCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.type = ErrorType.SERVER_PAYMENT_ERROR;
    }

    public static PaymentErrorResponseCode setPaymentFailedMessage(String message) {
        PAYMENT_FAILED.message = "결제 처리 중 오류가 발생했습니다. " + message;
        return PAYMENT_FAILED;
    }
}
