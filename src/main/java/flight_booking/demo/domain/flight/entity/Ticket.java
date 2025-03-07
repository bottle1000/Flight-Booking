package flight_booking.demo.domain.flight.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.airplane.entity.SeatState;
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
@NoArgsConstructor
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

	public void updateState(SeatState to) {
		this.state = to;
	}

}