package flight_booking.demo.domain.order.repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.utils.QuerydslUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static flight_booking.demo.domain.order.entity.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryRepository {
	private final JPAQueryFactory queryFactory;

	public Page<Order> findAllByUserId(Pageable pageable, String userId) {
		JPQLQuery<Order> query = queryFactory
			.selectFrom(order)
			.where(order.user.id.eq(userId));

		return QuerydslUtil.fetchPage(query, order, pageable);
	}
}
