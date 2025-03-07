package flight_booking.demo.domain.payment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import flight_booking.demo.domain.flight.entity.QTicket;
import flight_booking.demo.domain.order.entity.QOrder;
import flight_booking.demo.domain.order.entity.QOrderTicket;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.domain.payment.entity.PaymentState;
import flight_booking.demo.domain.payment.entity.QPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PaymentQueryRepositoryImpl implements PaymentQueryRepository{

    private final JPAQueryFactory factory;
    private final QPayment payment = QPayment.payment;
    private final QOrder order = QOrder.order;
    private final QOrderTicket orderTicket = QOrderTicket.orderTicket;
    private final QTicket ticket = QTicket.ticket;

    private static final int EXPIRED_MINUTES = 10;

    @Override
    public List<Payment> findAllExpired() {

        return factory
                .selectFrom(payment)
                .join(payment.order, order).fetchJoin()
                .join(order.tickets, orderTicket).fetchJoin()
                .join(orderTicket.ticket, ticket).fetchJoin()
                .where(
                        payment.state.eq(PaymentState.IN_PROGRESS),
                        payment.createdAt.before(LocalDateTime.now().minusMinutes(EXPIRED_MINUTES)))
                .fetch();
    }
}
