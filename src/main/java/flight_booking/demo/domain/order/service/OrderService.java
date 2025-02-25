package flight_booking.demo.domain.order.service;

import static flight_booking.demo.common.exception.ServerErrorResponseCode.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flight_booking.demo.common.event.PaymentRefundEvent;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.domain.airplane.entity.SeatState;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.Ticket;
import flight_booking.demo.domain.flight.repository.TicketRepository;
import flight_booking.demo.domain.order.dto.request.OrderCreateRequestDto;
import flight_booking.demo.domain.order.dto.request.OrderUpdateRequestDto;
import flight_booking.demo.domain.order.dto.response.OrderResponseDto;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.entity.OrderState;
import flight_booking.demo.domain.order.repository.OrderRepository;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.security.utils.UserUtil;
import flight_booking.demo.utils.Page;
import flight_booking.demo.utils.PageQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final DiscountRepository discountRepository;
	private final TicketRepository ticketRepository;
	private final ApplicationEventPublisher eventPublisher;

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
		if (ticket.getState() == SeatState.UNAVAILABLE)
			throw new CustomException(UNAVAILABLE_SEAT);

		FlightPlan flightPlan = ticket.getFlightPlan();

		Set<Discount> discounts = dto.discountIds().stream()
			.map(discountRepository::findById)
			.map(discount -> discount.orElseThrow(() -> new CustomException(DISCOUNT_NOT_FOUND)))
			.collect(Collectors.toSet());

		discounts.add(getUserMembershipDiscount(user));

		Order order = new Order(
			user,
			ticket,
			calculatePrice(flightPlan.getPrice(), discounts)
		);

		discounts.forEach(discount -> order.getPayment().addDiscount(discount));

		return OrderResponseDto.from(orderRepository.save(order));
	}

	//좌석변경
	@Transactional
	public OrderResponseDto update(Long id, OrderUpdateRequestDto dto) {
		Order order = orderRepository.findById(id)
			.orElseThrow(() -> new CustomException(CANNOT_FIND_ORDER));

		if (order.getState() == OrderState.CANCELED)
			throw new CustomException(ALREADY_CANCELED);

		Ticket ticketForChange = ticketRepository.findById(dto.ticketId())
			.orElseThrow(() -> new CustomException(SEAT_NOT_FOUND));

		order.changeTicket(ticketForChange);

		order = orderRepository.save(order);
		return OrderResponseDto.from(order);
	}

	private int calculatePrice(int price, Set<Discount> discounts) {
		int totalDiscountRate = 0;
		int totalDiscountAmount = 0;
		int discountedPrice = price;

		for (Discount discount : discounts) {
			totalDiscountRate += discount.getRate();
			totalDiscountAmount += discount.getAmount();
		}

		if (totalDiscountRate > 0) {
			discountedPrice = Double.valueOf(price * (1 - (totalDiscountRate / 100.0))).intValue();
		}

		return discountedPrice - totalDiscountAmount;
	}

	private Discount getUserMembershipDiscount(User user) {
		DiscountType type;
		switch (user.getMembership()) {
			case BASIC -> type = DiscountType.BASIC;
			case PREMIUM -> type = DiscountType.PREMIUM;
			case VIP -> type = DiscountType.VIP;
			default -> throw new CustomException(MEMBERSHIP_NOT_FOUND);
		}

        return discountRepository.findByGrade(type);
    }

	@Transactional
	public void cancel(Long id) {
		Order order = orderRepository.findById(id)
			.orElseThrow(() -> new CustomException(ORDER_ID_NOT_FOUND));
		order.updateState(OrderState.CANCELING);

		eventPublisher.publishEvent(new PaymentRefundEvent(order.getPayment().getUid()));
	}
}