package flight_booking.demo.common;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
	// 입력 문자열 포맷 (타임존 정보 없음)
	private static final DateTimeFormatter FORMATTER =
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	@Override
	public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String dateString = p.getText();
		// 입력된 문자열을 LocalDateTime으로 파싱
		LocalDateTime localDateTime = LocalDateTime.parse(dateString, FORMATTER);
		// 기본 타임존(Asia/Seoul)을 적용하여 ZonedDateTime 생성
		return ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul"));
	}
}
