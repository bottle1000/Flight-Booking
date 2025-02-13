package flight_booking.demo.domain.airplane.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.flight.dto.request.FlightPlanUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Airplane extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	private Airplane(String name) {
		this.name = name;
	}

	public static Airplane create(String name) {
		return new Airplane(name);
	}

	public void update(FlightPlanUpdateRequest flightPlanUpdateRequest) {

	}
}