package flight_booking.demo.domain.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import flight_booking.demo.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import static flight_booking.demo.domain.order.entity.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<Order> findAllByUserId(String userId) {
        return queryFactory
                .selectFrom(order)
                .where(order.user.id.eq(userId))
                .fetch();
    }
}
