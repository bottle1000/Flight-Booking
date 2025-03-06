package flight_booking.demo.domain.payment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import flight_booking.demo.domain.flight.entity.QTicket;
import flight_booking.demo.domain.order.entity.QOrder;
import flight_booking.demo.domain.payment.dto.PaymentQueryDto;
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
    private final QTicket ticket = QTicket.ticket;

    @Override
    public List<PaymentQueryDto> findByStateAndCreatedAtBefore(PaymentState paymentState, LocalDateTime tenMinutesAge) {

        return factory
                .select(Projections.fields(
                        PaymentQueryDto.class,
                        payment.id.as("paymentId")
                ))
                .from(payment)
                .where(
                        payment.state.eq(paymentState),
                        payment.createdAt.before(tenMinutesAge))
                .fetch();
    }
}
