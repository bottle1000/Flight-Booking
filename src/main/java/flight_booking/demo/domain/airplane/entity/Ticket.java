package flight_booking.demo.domain.airplane.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.flight.entity.FlightPlan;
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
public class Ticket extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String seat;

	@Enumerated(EnumType.STRING)
	private SeatState state = SeatState.AVAILABLE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flight_id")
	private FlightPlan flightPlan;

	public Ticket(String seat, FlightPlan flightPlan) {
		this.seat = seat;
		this.flightPlan = flightPlan;
	}

	// 주문 취소 시 좌석을 "이용 가능" 상태로 변경하는 메서드
	public void makeAvailable() {
		this.state = SeatState.AVAILABLE;
	}

}