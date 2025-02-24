package flight_booking.demo.common;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
	private static final DateTimeFormatter FORMATTER =
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
			.withZone(ZoneId.of("Asia/Seoul"));

	@Override
	public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String dateString = p.getText();
		if (dateString == null || dateString.isEmpty()) {
			return null;
		}
		try {
			LocalDateTime localDateTime = LocalDateTime.parse(dateString, FORMATTER);
			return localDateTime.atZone(ZoneId.of("Asia/Seoul"));
		} catch (DateTimeParseException e) {
			throw new JsonParseException(p, "Failed to parse date: " + dateString, e);
		}
	}
}