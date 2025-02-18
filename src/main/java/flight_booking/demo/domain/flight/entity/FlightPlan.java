package flight_booking.demo.domain.flight.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.airplane.entity.Airplane;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FlightPlan extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Airport departure;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Airport arrival;

	@Column(nullable = false)
	private int price;

	private LocalDateTime boardingAt;
	private LocalDateTime landingAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "airplane_id")
	private Airplane airplane;

	/**
	 * SINWOO
	 * 현재 결제 정보에서 결제 상품명을 FlightPlan 의 Description + Ticket 의 Seat 정보를 합쳐서 만들고 있습니다.
	 * FlightPlan 에 어떤 비행스케줄인지에 대한 Description 혹은 FlightPlan 의 Name 을 추가해 주시기 바랍니다.
	 */

	private FlightPlan(
			Airport departure,
			Airport arrival,
			int price,
			LocalDateTime boardingAt,
			LocalDateTime landingAt,
			Airplane airplane
	) {
		this.departure = departure;
		this.arrival = arrival;
		this.price = price;
		this.boardingAt = boardingAt;
		this.landingAt = landingAt;
		this.airplane = airplane;
	}

	public static FlightPlan create(
			Airport departure,
			Airport arrival,
			int price,
			LocalDateTime boardingAt,
			LocalDateTime landingAt,
			Airplane airplane
	) {
		return new FlightPlan(
				departure,
				arrival,
				price,
				boardingAt,
				landingAt,
				airplane);
	}

	public void update(
			Airport departure,
			Airport arrival,
			LocalDateTime boardingAt,
			LocalDateTime landingAt
	) {
		this.departure = departure;
		this.arrival = arrival;
		this.boardingAt = boardingAt;
		this.landingAt = landingAt;
	}
}