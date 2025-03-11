package flight_booking.demo.common.exception.payment.client;

import flight_booking.demo.common.exception.ErrorType;
import flight_booking.demo.common.exception.payment.PaymentErrorResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum ClientPaymentErrorResponseCode {
    // CANNOT RETRYABLE
    ALREADY_REFUND_PAYMENT(HttpStatus.BAD_REQUEST, "이미 환불된 결제입니다.."),
    PAY_PROCESS_CANCELED(HttpStatus.BAD_REQUEST, "결제가 사용자에 의해 취소되었습니다."),
    ALREADY_PROCESSED_PAYMENT(HttpStatus.BAD_REQUEST, "이미 처리된 결제 입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 결제 요청입니다."),
    EXCEED_MAX_PAYMENT_AMOUNT(HttpStatus.BAD_REQUEST, "하루 결제 가능 금액을 초과했습니다."),
    INVALID_AUTHORIZE_AUTH(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 방식입니다."),
    INVALID_CARD_LOST_OR_STOLEN(HttpStatus.BAD_REQUEST, "분실 혹은 도난 카드입니다."),
    RESTRICTED_TRANSFER_ACCOUNT(HttpStatus.BAD_REQUEST, "계좌는 등록 후 12시간 뒤부터 결제할 수 있습니다. 관련 정책은 해당 은행으로 문의해주세요."),
    INVALID_CARD_NUMBER(HttpStatus.BAD_REQUEST, "카드번호를 다시 확인해주세요."),
    EXCEED_MAX_ONE_DAY_WITHDRAW_AMOUNT(HttpStatus.BAD_REQUEST, "1일 출금 한도를 초과했습니다."),
    EXCEED_MAX_ONE_TIME_WITHDRAW_AMOUNT(HttpStatus.BAD_REQUEST, "1회 출금 한도를 초과했습니다."),
    INVALID_ACCOUNT_INFO_RE_REGISTER(HttpStatus.BAD_REQUEST, "유효하지 않은 계좌입니다. 계좌 재등록 후 시도해주세요."),
    NOT_AVAILABLE_PAYMENT(HttpStatus.BAD_REQUEST, "결제가 불가능한 시간대입니다."),
    REJECT_TOSSPAY_INVALID_ACCOUNT(HttpStatus.FORBIDDEN, "선택하신 출금 계좌가 출금이체 등록이 되어 있지 않아요. 계좌를 다시 등록해 주세요."),
    REJECT_CARD_PAYMENT(HttpStatus.FORBIDDEN, "한도초과 혹은 잔액부족으로 결제에 실패했습니다."),
    INVALID_PASSWORD(HttpStatus.FORBIDDEN, "결제 비밀번호가 일치하지 않습니다."),
    NOT_FOUND_PAYMENT_SESSION(HttpStatus.NOT_FOUND, "결제 시간이 만료되어 결제 진행 데이터가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
    private final ErrorType type;

    ClientPaymentErrorResponseCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.type = ErrorType.CLIENT_PAYMENT_ERROR;
    }

    public static Optional<ClientPaymentErrorResponseCode> has(String code) {
        return Arrays.stream(ClientPaymentErrorResponseCode.values())
                .filter(error -> error.name().equalsIgnoreCase(code))
                .findFirst();
    }
}
