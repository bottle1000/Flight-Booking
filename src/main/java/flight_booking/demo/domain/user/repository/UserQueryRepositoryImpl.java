package flight_booking.demo.domain.user.repository;

import static flight_booking.demo.domain.user.entity.QUser.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.utils.QuerydslUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {
	private final JPAQueryFactory queryFactory;

	public Page<User> findAllByUserId(Pageable pageable) {
		JPQLQuery<User> query = queryFactory
			.selectFrom(user);

		return QuerydslUtil.fetchPage(query, user, pageable);
	}
}
