package flight_booking.demo.sentry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;

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
