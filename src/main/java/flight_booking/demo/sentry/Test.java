package flight_booking.demo.sentry;

import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class Test {

	@GetMapping("/sentry")
	public String testSentry() {
		try {
			throw new Exception("This is a test.");
		} catch (Exception e) {
			// 로그에 에러 기록 (콘솔, SentryAppender 등에서 처리됨)
			log.error("테스트 중 발생한 에러: {}", e.getMessage(), e);
			// Sentry에 예외 전송
			Sentry.captureException(e);
		}
		return "에러 로그가 전송되었습니다.";
	}
}