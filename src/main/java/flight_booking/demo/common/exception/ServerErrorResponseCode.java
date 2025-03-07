package flight_booking.demo.common.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public enum ServerErrorResponseCode {

    //  사용자 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "입력하신 이메일을 찾을 수 없습니다. 다시 확인해주세요."),
    USER_ALREADY_DELETE(HttpStatus.BAD_REQUEST, "이미 탈퇴 처리된 회원입니다."),
    ID_MISMATCH(HttpStatus.UNAUTHORIZED, "권한이 존재하지 않습니다."),
    MEMBERSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "회원의 등급을 찾을 수 없습니다."),

    //  비행기 예매 관련
    FLIGHT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 항공편을 찾을 수 없습니다."),
    SEAT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 좌석을 찾을 수 없습니다."),
    UNAVAILABLE_SEAT(HttpStatus.NOT_ACCEPTABLE, "해당 좌석은 이용하실 수 없습니다."),
    SEAT_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "선택한 좌석이 이미 예약되었습니다."),
    CANNOT_CHANGE_SAME_SEAT(HttpStatus.BAD_REQUEST, "좌석변경은 예약한 좌석과 다른 좌석으로만 변경이 가능합니다."),
    INVALID_FLIGHT_DATE(HttpStatus.BAD_REQUEST, "유효하지 않은 출발 날짜입니다."),
    BOOKING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "항공권 예매 중 오류가 발생했습니다."),
    BOOKING_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 예약입니다."),
    BOOKING_NOT_FOUND(HttpStatus.NOT_FOUND, "예약 정보를 찾을 수 없습니다."),
    USER_NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "이 예약에 대한 권한이 없습니다."),
    PAYMENT_REQUIRED(HttpStatus.PAYMENT_REQUIRED, "결제를 완료해야 예약이 확정됩니다."),
    EXCEED_MAX_TICKETS(HttpStatus.BAD_REQUEST, "최대 예매 가능한 항공권 개수를 초과했습니다."),
    TOO_MANY_FLIGHT(HttpStatus.BAD_REQUEST, "다수의 비행스케줄을 동시에 예매할 수 없습니다."),

    // 비행기, 비행기 일정 관련
    AIRPLANE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 항공기를 찾을 수 없습니다."),
    FLIGHTPLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 항공 일정을 찾을 수 없습니다."),
    FLIGHTPLAN_ALREADY_EXISTS(HttpStatus.CONFLICT, "해당 항공편이 이미 존재합니다."),

    //  OAuth 2.0 관련
    OAUTH2_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "OAuth2.0 로그인에 실패했습니다."),
    OAUTH2_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "OAuth2.0 사용자 정보를 찾을 수 없습니다."),
    OAUTH2_ACCESS_DENIED(HttpStatus.FORBIDDEN, "OAuth2.0 인증에 실패했습니다."),
    OAUTH2_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "OAuth2.0 액세스 토큰이 만료되었습니다."),
    OAUTH2_REGISTRATION_REQUIRED(HttpStatus.BAD_REQUEST, "OAuth2.0 회원가입이 필요합니다."),

    // 할인 정보 관련
    DISCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 할인 정보를 찾을 수 없습니다."),
    DISCOUNT_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "지원하지 않는 할인 유형입니다."),
    RATE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "할인 비율은 0~100 사이여야 합니다."),
    AMOUNT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "금액은 0 이상의 숫자여야합니다."),
    INVALID_END_AT(HttpStatus.BAD_REQUEST, "종료일은 시작일과 같거나 앞설 수 없습니다."),
    MEMBERSHIP_DISCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버쉽 할인이 존재하지 않습니다."),
    INVALID_MEMBERSHIP_DISCOUNT(HttpStatus.BAD_REQUEST, "유효한 멤버쉽 할인이 없거나 여러개입니다."),
    FILTER_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "멤버쉽 할인만 조회할 수 있습니다."),
    DISCOUNT_EXPIRED(HttpStatus.BAD_REQUEST, "유효기간이 만료된 할인입니다."),

    // 주문 관련
    CANNOT_FIND_ORDER(HttpStatus.NOT_FOUND, "요청하신 주문내역은 존재하지 않습니다."),
    ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 주문은 변경할 수 없습니다."),
    NOT_PAID(HttpStatus.BAD_REQUEST, "결제가 완료되지 않아 진행할 수 없습니다."),
    ORDER_UUID_NOT_FOUND(HttpStatus.NOT_FOUND, "주문한 상품의 UUID 를 찾을 수 없습니다."),
    INVALID_ORDER_STATE(HttpStatus.BAD_REQUEST, "유효하지 않은 주문 상태 요청입니다."),
    ORDER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "주문 ID를 찾을 수 없습니다."),

    //  기타 공통 예외
    URL_NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 경로입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),
    RESOURCE_CONFLICT(HttpStatus.CONFLICT, "요청한 리소스가 충돌합니다."),
    NETWORK_ERROR(HttpStatus.REQUEST_TIMEOUT, "일시적인 네트워크 오류가 발생했습니다."),
    INVALID_ERROR_TYPE(HttpStatus.NOT_ACCEPTABLE,"해당 오류는 오류 분류가 잘못되어있습니다."),

    // Lock
    LOCK_CONFLICT(HttpStatus.LOCKED, "Lock 획득을 시도하였으나 실패하였습니다. Thread ID: " + Thread.currentThread().getId());

    private final HttpStatus status;
    private final String message;
    private final ErrorType type;

    ServerErrorResponseCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.type = ErrorType.SERVER_ERROR;
    }
}
