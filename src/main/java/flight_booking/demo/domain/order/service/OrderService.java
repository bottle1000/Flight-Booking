package flight_booking.demo.domain.order.service;

import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.invoice.repository.InvoiceRepository;
import flight_booking.demo.domain.order.dto.request.OrderCreateRequestDto;
import flight_booking.demo.domain.order.dto.request.OrderUpdateRequestDto;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.repository.OrderRepository;
import flight_booking.demo.domain.payment.repository.PaymentRepository;
import flight_booking.demo.domain.ticket.entity.Ticket;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.security.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final DiscountRepository discountRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public Order find(long id) {
        //TODO: GlobalExceptionHandler
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 주문내역을 찾을 수 없습니다."));
    }

    public List<Order> findAll(long userId) {
        return orderRepository.findAllByUserId(String.valueOf(userId));
    }

    @Transactional
    public Order create(OrderCreateRequestDto dto) {
        User user = UserUtil.getCurrentUser();
        /**
         * TODO
         * Ticket ticket = flightRepository.findTicket(dto.ticketId);
         * FlightPlan flightPlan = ticket.getFlightPlan();
         */
        Ticket ticket = new Ticket(null, null);
        FlightPlan flightPlan = new FlightPlan(null, null, null, null, null, 50000);

        List<Discount> discounts = dto.discountIds().stream()
                .map(discountRepository::findById)
                //TODO: GlobalExceptionHandler
                .map(discount -> discount.orElseThrow(() -> new RuntimeException("유효한 할인 항목이 아닙니다.")))
                .toList();

        Order order = new Order(
                user,
                ticket,
                calculatePrice(flightPlan.getPrice(), discounts)
        );

        discounts.forEach(discount -> order.getPayment().addDiscount(discount));
        orderRepository.save(order);

        return order;
    }

    //결제취소 및 좌석변경 및
    @Transactional
    public Order update(Long id, OrderUpdateRequestDto dto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 주문내역을 찾을 수 없습니다."));

        //TODO: Ticket changedTicket = ticketRepository.find(dto.ticketId)
        // .orElseThrow(() -> new RuntimeException("해당 티켓: " + dto.ticketId() +" 을 찾을 수 없습니다."));;
        Ticket ticket = new Ticket(null, null);

        order.changeTicket(ticket);
        order.updateState(dto.orderState());
        //TODO: 결제정보 변경(결제 취소 등)의 경우, Payment 에서 EventHandler 로 작업

        return orderRepository.save(order);
    }

    private int calculatePrice(int price, List<Discount> discounts) {
        int totalDiscountRate = 0;
        int totalDiscountAmount = 0;

        for (Discount discount : discounts) {
            totalDiscountRate += discount.getRate();
            totalDiscountAmount += discount.getAmount();
        }

        if (totalDiscountRate > 0) {
            price = price * (1 - totalDiscountRate / 100);
        }

        return price - totalDiscountAmount;
    }
}