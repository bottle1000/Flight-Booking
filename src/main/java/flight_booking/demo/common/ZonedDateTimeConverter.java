package flight_booking.demo.common;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

@Component
public class ZonedDateTimeConverter implements Converter<String, ZonedDateTime> {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	@Override
	public ZonedDateTime convert(String source) {
		if (source == null || source.isEmpty()) {
			return null;
		}
		return ZonedDateTime.parse(source, FORMATTER.withZone(ZoneId.of("Asia/Seoul")));
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		return typeFactory.constructType(ZonedDateTime.class);
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		return typeFactory.constructType(ZonedDateTime.class);
	}
}
