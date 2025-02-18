package flight_booking.demo.domain.order.service;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.domain.airplane.entity.SeatState;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.flight.repository.TicketRepository;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.order.dto.request.OrderCreateRequestDto;
import flight_booking.demo.domain.order.dto.request.OrderUpdateRequestDto;
import flight_booking.demo.domain.order.dto.response.OrderResponseDto;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.repository.OrderRepository;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.security.utils.UserUtil;
import flight_booking.demo.utils.Page;
import flight_booking.demo.utils.PageQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static flight_booking.demo.common.exception.ResponseCode.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final DiscountRepository discountRepository;
    private final TicketRepository ticketRepository;

    public OrderResponseDto find(long id) {
        return OrderResponseDto.from(
                orderRepository.findById(id)
                        .orElseThrow(() -> new CustomException(CANNOT_FIND_ORDER)));
    }

    public Page<OrderResponseDto> findAll(PageQuery pageQuery) {
        org.springframework.data.domain.Page<Order> page =
                orderRepository.findAllByUserId(pageQuery.toPageable(), UserUtil.getCurrentUserId());

        return Page.from(page.map(OrderResponseDto::from));
    }

    @Transactional
    public OrderResponseDto create(OrderCreateRequestDto dto) {
        User user = UserUtil.getCurrentUser();

        Ticket ticket = ticketRepository.findById(dto.ticketId())
                .orElseThrow(() -> new CustomException(SEAT_NOT_FOUND));
        if(ticket.getState() == SeatState.UNAVAILABLE)
            throw new CustomException(UNAVAILABLE_SEAT);

        FlightPlan flightPlan = ticket.getFlightPlan();

        /**
         * TODO
         * User 의 Membership 등급 할인이 적용되어야 합니다.
         * DiscountRepository 에 등급검색이 존재하지 않습니다.
         *
         * Discount membershipDiscount = discountRepository.findByDiscountType(DiscountType.GRADE);
         */
        List<Discount> discounts = dto.discountIds().stream()
                .map(discountRepository::findById)
                .map(discount -> discount.orElseThrow(() -> new CustomException(DISCOUNT_NOT_FOUND)))
                .toList();

        Order order = new Order(
                user,
                ticket,
                calculatePrice(flightPlan.getPrice(), discounts)
        );

        discounts.forEach(discount -> order.getPayment().addDiscount(discount));

        return OrderResponseDto.from(orderRepository.save(order));
    }

    //결제취소 및 좌석변경
    @Transactional
    public OrderResponseDto update(Long id, OrderUpdateRequestDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException(CANNOT_FIND_ORDER));

        Ticket ticketForChange = ticketRepository.findById(dto.ticketId())
                .orElseThrow(() -> new CustomException(SEAT_NOT_FOUND));

        order.changeTicket(ticketForChange);

        //TODO: 결제정보 변경(결제 취소 등)은 EventHandler 로 작업해야 합니다. Payment 병합 후 작업예정입니다.
        order.updateState(dto.orderState());

        order = orderRepository.save(order);
        return OrderResponseDto.from(order);
    }

    private int calculatePrice(int price, List<Discount> discounts) {
        int totalDiscountRate = 0;
        int totalDiscountAmount = 0;
        int discountedPrice = 0;

        for (Discount discount : discounts) {
            totalDiscountRate += discount.getRate();
            totalDiscountAmount += discount.getAmount();
        }

        if (totalDiscountRate > 0) {
            discountedPrice = Double.valueOf(price*(1-(totalDiscountRate/100.0))).intValue();
        }

        return discountedPrice - totalDiscountAmount;
    }
}