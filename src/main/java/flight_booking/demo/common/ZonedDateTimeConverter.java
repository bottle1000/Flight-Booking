package flight_booking.demo.common;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {
	private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

	@Override
	public Timestamp convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
		if (zonedDateTime == null) return null;
		return Timestamp.valueOf(zonedDateTime
			.withZoneSameInstant(ZONE_ID)
			.truncatedTo(ChronoUnit.SECONDS)  // 나노초 제거
			.toLocalDateTime());
	}

	@Override
	public ZonedDateTime convertToEntityAttribute(Timestamp timestamp) {
		if (timestamp == null) return null;
		return timestamp.toLocalDateTime()
			.atZone(ZONE_ID);
	}
}