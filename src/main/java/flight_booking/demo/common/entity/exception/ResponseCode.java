package flight_booking.demo.common.entity.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public enum ResponseCode {

    //  사용자 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "입력하신 이메일을 찾을 수 없습니다. 다시 확인해주세요."),
    USER_ALREADY_DELETE(HttpStatus.BAD_REQUEST, "이미 탈퇴 처리된 회원입니다."),
    ID_MISMATCH(HttpStatus.UNAUTHORIZED, "권한이 존재하지 않습니다."),

    //  비행기 예매 관련
    FLIGHT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 항공편을 찾을 수 없습니다."),
    SEAT_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "선택한 좌석이 이미 예약되었습니다."),
    INVALID_FLIGHT_DATE(HttpStatus.BAD_REQUEST, "유효하지 않은 출발 날짜입니다."),
    BOOKING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "항공권 예매 중 오류가 발생했습니다."),
    BOOKING_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 예약입니다."),
    BOOKING_NOT_FOUND(HttpStatus.NOT_FOUND, "예약 정보를 찾을 수 없습니다."),
    USER_NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "이 예약에 대한 권한이 없습니다."),
    PAYMENT_REQUIRED(HttpStatus.PAYMENT_REQUIRED, "결제를 완료해야 예약이 확정됩니다."),
    EXCEED_MAX_TICKETS(HttpStatus.BAD_REQUEST, "최대 예매 가능한 항공권 개수를 초과했습니다."),

    //  OAuth 2.0 관련
    OAUTH2_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "OAuth2.0 로그인에 실패했습니다."),
    OAUTH2_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "OAuth2.0 사용자 정보를 찾을 수 없습니다."),
    OAUTH2_ACCESS_DENIED(HttpStatus.FORBIDDEN, "OAuth2.0 인증에 실패했습니다."),
    OAUTH2_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "OAuth2.0 액세스 토큰이 만료되었습니다."),
    OAUTH2_REGISTRATION_REQUIRED(HttpStatus.BAD_REQUEST, "OAuth2.0 회원가입이 필요합니다."),

    //  Toss 결제 관련
    PAYMENT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "결제 처리 중 오류가 발생했습니다."),
    PAYMENT_CANCELED(HttpStatus.BAD_REQUEST, "사용자가 결제를 취소했습니다."),
    INVALID_PAYMENT_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 결제 요청입니다."),
    PAYMENT_VERIFICATION_FAILED(HttpStatus.PAYMENT_REQUIRED, "결제 검증에 실패했습니다."),
    REFUND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "환불 처리 중 오류가 발생했습니다."),
    REFUND_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "이 결제는 환불이 불가능합니다."),
    PAYMENT_ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "이미 처리된 결제입니다."),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 정보를 찾을 수 없습니다."),

    //  기타 공통 예외
    URL_NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 경로입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),
    RESOURCE_CONFLICT(HttpStatus.CONFLICT, "요청한 리소스가 충돌합니다.");

    private final HttpStatus status;
    private final String message;

    ResponseCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
