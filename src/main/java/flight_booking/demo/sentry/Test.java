package flight_booking.demo.sentry;
import java.lang.Exception;

import flight_booking.demo.domain.airplane.dto.response.AirplaneGetListResponse;
import flight_booking.demo.utils.Page;
import flight_booking.demo.utils.PageQuery;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class Test {
    @GetMapping("/sentry")
    public void testSentry() {
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
    }

}
