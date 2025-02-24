package flight_booking.demo.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		// 명시적 타입 지정으로 형변환 불필요
		registry.addConverter(String.class, ZonedDateTime.class, new StringToZonedDateTimeConverter());
	}

	private static class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {
		@Override
		public ZonedDateTime convert(String source) {
			if (source == null || source.trim().isEmpty()) {
				return null;
			}

			try {
				// 날짜만 있는 경우 (yyyy-MM-dd)
				if (source.length() == 10) {
					LocalDate date = LocalDate.parse(source);
					return date.atStartOfDay(ZoneId.of("Asia/Seoul"));
				}
				// 날짜+시간이 있는 경우 (yyyy-MM-ddTHH:mm:ss)
				else {
					LocalDateTime dateTime = LocalDateTime.parse(source);
					return dateTime.atZone(ZoneId.of("Asia/Seoul"));
				}
			} catch (DateTimeParseException e) {
				throw new IllegalArgumentException("Invalid date format: " + source);
			}
		}
	}
}
