package flight_booking.demo.common.entity.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ResponseCode responseCode = ex.getResponseCode();
        ErrorResponse errorResponse = new ErrorResponse(responseCode.getStatus().value(), responseCode.getMessage());
        return new ResponseEntity<>(errorResponse, responseCode.getStatus());
    }

    // 예외 응답을 위한 내부 클래스
    private record ErrorResponse(int status, String message) {}
}
