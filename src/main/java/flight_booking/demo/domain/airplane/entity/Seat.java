package flight_booking.demo.domain.airplane.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.ticket.entity.Ticket;
import flight_booking.demo.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Seat extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String line;

	@Enumerated(EnumType.STRING)
	private SeatState state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "airplane_id")
	private Airplane airplane;

	@OneToOne(mappedBy = "seat")
	private Ticket ticket;

	@Builder
	public Seat(String line, SeatState state, Airplane airplane, User user) {
		this.line = line;
		this.state = state;
		this.airplane = airplane;
	}

	public void assignTicket(Ticket ticket) {
		this.ticket = ticket;
		ticket.setSeat(this);
	}
}