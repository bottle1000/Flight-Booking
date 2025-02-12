package flight_booking.demo.domain.flight.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AirTerminal {
	KOREAN_AIR("KE", "대한항공"),
	ASIANA("OZ", "아시아나항공"),
	JEJU("7C", "제주항공"),
	JIN_AIR("LJ", "진에어"),
	AIR_BUSAN("BX", "에어부산"),
	EASTAR("ZE", "이스타항공"),
	T_WAY("TW", "티웨이항공"),
	AIR_SEOUL("RS", "에어서울");

	private final String code;
	private final String description;

	@JsonCreator
	public static AirTerminal from(String value) {
		try {
			return Arrays.stream(AirTerminal.values())
				.filter(airTerminal -> airTerminal.getCode().equals(value))
				.findFirst()
				.orElseGet(() -> {
					try {
						return AirTerminal.valueOf(value.toUpperCase());
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException("Invalid airline code: " + value);
					}
				});
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid airline code: " + value);
		}
	}

	@JsonValue
	public String getCode() {
		return code;
	}
}
