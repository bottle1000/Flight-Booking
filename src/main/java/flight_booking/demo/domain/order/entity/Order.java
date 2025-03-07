package flight_booking.demo.domain.order.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.domain.airplane.entity.SeatState;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.payment.entity.PaymentState;
import flight_booking.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static flight_booking.demo.common.exception.ServerErrorResponseCode.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderTicket> tickets = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL)
	private Payment payment;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderState state = OrderState.NOT_PAID;

	private int price;

	public Order(User user, List<Ticket> tickets, int price) {
		this.user = user;
		tickets.forEach(this::addTicket);
		this.price = price;
		this.payment = new Payment(this);
	}

	public List<Long> getTicketIds() {
		return tickets.stream()
				.map(orderTicket -> orderTicket.getTicket().getId())
				.toList();
	}

	public String getOrderName() {
		List<Ticket> ticketList = tickets.stream()
				.map(OrderTicket::getTicket)
				.toList();

		if (ticketList.isEmpty()) return "";

		String flightName = ticketList.get(0).getFlightPlan().getName();
		List<String> seatNumbers = ticketList.stream()
				.limit(3)
				.map(Ticket::getSeat)
				.toList();

		String orderName = flightName + " " + String.join(", ", seatNumbers);

		if (ticketList.size() > 4) {
			orderName += " 외 " + (ticketList.size() - 3) + "장";
		}

		return orderName;
	}

	public void updateTickets(List<Ticket> newTickets) {
		Set<Ticket> currentTickets = tickets.stream()
				.map(OrderTicket::getTicket)
				.collect(Collectors.toSet());

		Set<Ticket> ticketsForAdd = new HashSet<>(newTickets);
		ticketsForAdd.removeAll(currentTickets);

		Set<Ticket> ticketsForRemove = new HashSet<>(currentTickets);
		newTickets.forEach(ticketsForRemove::remove);

		ticketsForRemove.forEach(this::removeTicket);
		ticketsForAdd.forEach(this::addTicket);
	}

	public void updateState(OrderState orderState) {
		if (this.state == OrderState.CANCELED) {
			throw new CustomException(ALREADY_CANCELED);
		}

		if (this.state != OrderState.PAID && orderState == OrderState.PAID &&
				this.payment.getState() != PaymentState.COMPLETE) {
			throw new CustomException(NOT_PAID);
		}

		this.state = orderState;
	}

	private void addTicket(Ticket ticket) {
		if (ticket.getState() == SeatState.UNAVAILABLE) {
			throw new CustomException(UNAVAILABLE_SEAT);
		}

		tickets.add(new OrderTicket(this, ticket));
	}

	private void removeTicket(Ticket ticket) {
		tickets.stream()
				.filter(orderTicket -> orderTicket.getTicket().equals(ticket))
				.findFirst()
				.ifPresent(orderTicket -> {
					orderTicket.getTicket().updateState(SeatState.IDLE);
					tickets.remove(orderTicket);
				});
	}
}