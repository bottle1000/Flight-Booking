package flight_booking.demo.domain.flight.entity;

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

	private ZonedDateTime boardingAt;
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
}