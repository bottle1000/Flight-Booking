package flight_booking.demo.domain.flight.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FlightPlan extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.STRING)
	@Column(name = "fromNational")
	private National from;

	@Enumerated(EnumType.STRING)
	@Column(name = "toNational")
	private National to;

	private LocalDateTime boardingAt;
	private LocalDateTime landingAt;
	private int price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "airplane_id")
	private Airplane airplane;

	@Builder
	public FlightPlan(Airplane airplane, National from, National to,
		LocalDateTime boardingAt, LocalDateTime landingAt, int price) {
		this.airplane = airplane;
		this.from = from;
		this.to = to;
		this.boardingAt = boardingAt;
		this.landingAt = landingAt;
		this.price = price;
	}

}