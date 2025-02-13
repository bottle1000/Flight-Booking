package flight_booking.demo.domain.order.service;

import flight_booking.demo.domain.discount.repository.DiscountRepository;
import flight_booking.demo.domain.flight.entity.Flight;
import flight_booking.demo.domain.order.dto.request.OrderCreateRequestDto;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.order.dto.request.OrderUpdateRequestDto;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.repository.OrderRepository;
import flight_booking.demo.domain.receipt.entity.Receipt;
import flight_booking.demo.domain.receipt.entity.ReceiptDiscount;
import flight_booking.demo.domain.receipt.repository.ReceiptDiscountRepository;
import flight_booking.demo.domain.receipt.repository.ReceiptRepository;
import flight_booking.demo.domain.ticket.entity.Ticket;
import flight_booking.demo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final DiscountRepository discountRepository;
    private final ReceiptRepository receiptRepository;
    private final ReceiptDiscountRepository receiptDiscountRepository;

    public Order find(int id) {
        //TODO: GlobalExceptionHandler
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 주문내역을 찾을 수 없습니다."));
    }

    @Transactional
    public Order create(OrderCreateRequestDto dto) {
        /**
         * TODO
         * Ticket ticket = flightRepository.findTicket(dto.ticketId);
         * FlightPlan flightPlan = ticket.getFlight();
         */
        User user = getUser();
        Ticket ticket = new Ticket();
        Flight flight = new Flight();

        Order order = new Order(
                user,
                ticket,
                1 // TODO:flight.getPrice();
        );

        List<Discount> discounts = dto.discountIds().stream()
                .map(discountRepository::findById)
                //TODO: GlobalExceptionHandler
                .map(discount -> discount.orElseThrow(() -> new RuntimeException("유효한 할인 항목이 아닙니다.")))
                .toList();

        /**
         * TODO
         * int discountedPrice = calculatePrice(flight, discounts);
         * if(paymentService.processPayment(user, discountedPrice)) {
         *      Receipt receipt = new Receipt(order, flight.getOriginalPrice(), discountedPrice));
         *      order.registerReceipt(receipt);
         *      registerReceiptDiscounts(receipt, discounts);
         * }
         *
         * orderRepository.save(order);
         */

        return order;
    }

    public Order update(Long id, OrderUpdateRequestDto dto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 주문내역을 찾을 수 없습니다."));
        order.getTicket()
    }

    private User getUser() {
        //TODO
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    private void registerReceiptDiscounts(Receipt receipt, List<Discount> discounts) {
        for (Discount discount : discounts) {
            ReceiptDiscount receiptDiscount = new ReceiptDiscount(receipt, discount);
            receiptDiscountRepository.save(receiptDiscount);
        }
    }

    private int calculatePrice(Flight flight, List<Discount> discounts) {
        /**
         * TODO
         * int orderPrice = flight.getPrice();
         */
        int price = 50000;
        int totalDiscountRate = 0;
        int totalDiscountAmount = 0;

        for (Discount discount : discounts) {
            totalDiscountRate += discount.getRate();
            totalDiscountAmount += discount.getAmount();
        }

        if(totalDiscountRate > 0){
            price = price * (1 - totalDiscountRate / 100);
        }

        return price - totalDiscountAmount;
    }
}