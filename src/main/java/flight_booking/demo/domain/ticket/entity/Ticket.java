package flight_booking.demo.domain.ticket.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.airplane.entity.Seat;
import flight_booking.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ticket extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id")
	private Seat seat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Ticket(Seat seat, User user) {
		this.seat = seat;
		this.user = user;
	}
}