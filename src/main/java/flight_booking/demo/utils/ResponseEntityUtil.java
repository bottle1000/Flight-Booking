package flight_booking.demo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {
    public static <T> ResponseEntity<T> success(String message, T body) {
        return ResponseEntity.ok(body);
    }
}
