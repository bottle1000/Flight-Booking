package flight_booking.demo.common.exception;

import flight_booking.demo.common.exception.payment.PaymentErrorResponseCode;
import flight_booking.demo.common.exception.payment.PaymentException;
import flight_booking.demo.common.exception.payment.client.ClientPaymentErrorResponseCode;
import flight_booking.demo.common.exception.payment.client.ClientPaymentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ServerErrorResponseCode serverErrorResponseCode = ex.getServerErrorResponseCode();
        ErrorResponse errorResponse = new ErrorResponse(serverErrorResponseCode.getStatus().value(), serverErrorResponseCode.getMessage());
        return new ResponseEntity<>(errorResponse, serverErrorResponseCode.getStatus());
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> handlePaymentException(PaymentException ex) {
        PaymentErrorResponseCode code = ex.getCode();
        ErrorResponse errorResponse = new ErrorResponse(code.getStatus().value(), code.getMessage());
        return new ResponseEntity<>(errorResponse, code.getStatus());
    }

    @ExceptionHandler(ClientPaymentException.class)
    public ResponseEntity<ErrorResponse> handleClientPaymentException(ClientPaymentException ex) {
        ClientPaymentErrorResponseCode code = ex.getCode();
        ErrorResponse errorResponse = new ErrorResponse(code.getStatus().value(), code.getMessage());
        return new ResponseEntity<>(errorResponse, code.getStatus());
    }

    // 예외 응답을 위한 내부 클래스
    private record ErrorResponse(int status, String message) {}
}
