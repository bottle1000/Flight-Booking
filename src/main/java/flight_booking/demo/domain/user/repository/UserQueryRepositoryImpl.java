package flight_booking.demo.domain.user.repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import flight_booking.demo.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import flight_booking.demo.utils.QuerydslUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static flight_booking.demo.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository{
    private final JPAQueryFactory queryFactory;
    public Page<User> findAllByUserId(Pageable pageable) {
        JPQLQuery<User> query = queryFactory
                .selectFrom(user);

        return QuerydslUtil.fetchPage(query, user, pageable);
    }
}
