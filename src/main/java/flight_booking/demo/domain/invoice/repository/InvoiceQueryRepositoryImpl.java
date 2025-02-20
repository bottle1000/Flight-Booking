package flight_booking.demo.domain.invoice.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ServerErrorResponseCode;
import flight_booking.demo.domain.invoice.entity.QInvoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class InvoiceQueryRepositoryImpl implements InvoiceQueryRepository{

    private final JPAQueryFactory factory;
    private final QInvoice invoice = QInvoice.invoice;

    @Override
    public String findKeyByPaymentId(Long id) {
         String result = factory
                .select(Projections.fields(invoice.paymentKey))
                .from(invoice)
                .where(invoice.payment.id.eq(id))
                .fetchOne();

        if (result.isBlank()) {
            throw new CustomException(ServerErrorResponseCode.NOT_PAID);
        }
        return result;
    }
}
