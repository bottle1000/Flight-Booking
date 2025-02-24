package flight_booking.demo.config;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import flight_booking.demo.common.CustomZonedDateTimeDeserializer;
import flight_booking.demo.common.CustomZonedDateTimeSerializer;

@Configuration
public class JacksonConfig implements WebMvcConfigurer {
	@Bean
	@Primary  // Spring이 기본 ObjectMapper로 사용하도록 설정
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		// JavaTimeModule 생성 후 커스텀 ZonedDateTime Serializer 등록
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		// 커스텀 Serializer 등록 (이미 있다면)
		javaTimeModule.addSerializer(java.time.ZonedDateTime.class, new CustomZonedDateTimeSerializer());
		// 커스텀 Deserializer 등록
		javaTimeModule.addDeserializer(java.time.ZonedDateTime.class, new CustomZonedDateTimeDeserializer());
		objectMapper.registerModule(javaTimeModule);

		// ISO-8601 문자열 형식으로 직렬화 (Timestamp가 아닌 문자열)
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 참고: 아래 설정은 java.util.Date에 적용됨.
		objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));

		return objectMapper;
	}


}
