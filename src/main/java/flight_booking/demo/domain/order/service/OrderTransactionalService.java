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

import static flight_booking.demo.common.exception.ServerErrorResponseCode.UNAVAILABLE_SEAT;

@Service
@RequiredArgsConstructor
public class OrderTransactionalService {
    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;

    @Lock(key = "#order.getTicket().getId()")
    @Transactional
    public Order checkAndSaveOrder(Order order) {
        Ticket ticket = ticketRepository.findTicketByNativeQuery(order.getTicket().getId());
        if (ticket.getState() != SeatState.IDLE) {
            throw new CustomException(UNAVAILABLE_SEAT);
        }
        ticket.updateState(SeatState.BOOKED);
        return orderRepository.save(order);
    }
}
