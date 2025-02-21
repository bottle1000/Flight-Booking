package flight_booking.demo.common;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {
	// 공통 포맷과 타임존 (Asia/Seoul) 설정
	private static final DateTimeFormatter FORMATTER =
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
			.withZone(ZoneId.of("Asia/Seoul"));

	@Override
	public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// ZonedDateTime을 공통 타임존(Asia/Seoul)으로 변환한 후 포맷팅
		ZonedDateTime converted = value.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
		String formatted = FORMATTER.format(converted);
		gen.writeString(formatted);
	}
}
