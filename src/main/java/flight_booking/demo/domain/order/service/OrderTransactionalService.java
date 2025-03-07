package flight_booking.demo.domain.order.service;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.domain.airplane.entity.SeatState;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.flight.repository.TicketRepository;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.repository.OrderRepository;
import flight_booking.demo.lock.Lock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static flight_booking.demo.common.exception.ServerErrorResponseCode.UNAVAILABLE_SEAT;

@Service
@RequiredArgsConstructor
public class OrderTransactionalService {
    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;

    @Lock(key = "#order.getOrderName()", prefix = "order_create_lock:")
    @Transactional
    public Order checkAndSaveOrder(Order order) {
        var ids = order.getTickets().stream().map(orderTicket -> orderTicket.getTicket().getId()).toList();
        List<Ticket> tickets = ticketRepository.findAllTicketByNativeQuery(ids);

        List<Ticket> unavailableTickets = tickets.stream().filter(ticket -> ticket.getState() != SeatState.IDLE).toList();
        if (!unavailableTickets.isEmpty())
            throw new CustomException(UNAVAILABLE_SEAT);

        tickets.forEach(ticket -> ticket.updateState(SeatState.BOOKED));
        return orderRepository.save(order);
    }

    @Lock(key = "#ticket.getId()", prefix = "booking_ticket_lock:")
    public Ticket checkAndSaveTicket(Ticket ticket) {

    }
}
