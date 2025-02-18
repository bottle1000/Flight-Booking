package flight_booking.demo.domain.airplane.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * SINWOO
 * 1.
 * Ticket 은 FlightPlan 에 종속됩니다.
 * FlightPlan Entity 폴더에 있어야 합니다.
 *
 * 2. 사용되지 않는 메소드는 삭제바랍니다.
 */
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
	private SeatState state = SeatState.IDLE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flightplan_id")
	private FlightPlan flightPlan;

	public Ticket(String seat, FlightPlan flightPlan) {
		this.seat = seat;
		this.flightPlan = flightPlan;
	}

	// 주문 취소 시 좌석을 "이용 가능" 상태로 변경하는 메서드
	public void makeAvailable() {
		this.state = SeatState.IDLE;
	}

	public void updateState(SeatState to) {
		this.state = to;
	}

}