package flight_booking.demo.domain.flight.entity;

import java.time.Duration;
import java.time.ZonedDateTime;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.airplane.entity.Airplane;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FlightPlan extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Airport departure;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Airport arrival;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false, columnDefinition = "DATETIME")
	private ZonedDateTime boardingAt;

	@Column(nullable = false, columnDefinition = "DATETIME")
	private ZonedDateTime landingAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "airplane_id")
	private Airplane airplane;

	private FlightPlan(String name, Airport departure, Airport arrival, int price, ZonedDateTime boardingAt,
		ZonedDateTime landingAt, Airplane airplane) {
		this.name = name;
		this.departure = departure;
		this.arrival = arrival;
		this.price = price;
		this.boardingAt = boardingAt;
		this.landingAt = landingAt;
		this.airplane = airplane;
	}

	// Querydsl 프로젝션용 생성자 추가
	public FlightPlan(Long id, Airport departure, Airport arrival,
					  ZonedDateTime boardingAt, ZonedDateTime landingAt,
					  Integer price) {
		this.id = id;
		this.departure = departure;
		this.arrival = arrival;
		this.boardingAt = boardingAt;
		this.landingAt = landingAt;
		this.price = price;
		// 다른 필드는 null 또는 기본값으로 유지됨
	}

	public static FlightPlan create(String name, Airport departure, Airport arrival, int price,
		ZonedDateTime boardingAt,
		ZonedDateTime landingAt, Airplane airplane) {
		return new FlightPlan(name, departure, arrival, price, boardingAt, landingAt, airplane);
	}

	public void update(Airport departure, Airport arrival, ZonedDateTime boardingAt, ZonedDateTime landingAt) {
		this.departure = departure;
		this.arrival = arrival;
		this.boardingAt = boardingAt;
		this.landingAt = landingAt;
	}

	public Duration flightDuration(FlightPlan flightPlan) {
		return Duration.between(flightPlan.boardingAt, flightPlan.landingAt);
	}
}
