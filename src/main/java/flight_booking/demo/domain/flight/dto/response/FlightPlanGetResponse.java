package flight_booking.demo.domain.flight.dto.response;

import java.util.List;

import flight_booking.demo.domain.flight.entity.Ticket;

public record FlightPlanGetResponse(List<Ticket> ticketList, int ticketCount ) {
	public static FlightPlanGetResponse from(List<Ticket> ticketList, int ticketCount) {
		return new FlightPlanGetResponse(ticketList, ticketCount);
	}
}
