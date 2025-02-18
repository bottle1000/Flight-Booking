package flight_booking.demo.domain.discount.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;
import flight_booking.demo.utils.QuerydslUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static flight_booking.demo.domain.discount.entity.QDiscount.discount;

@Repository
@RequiredArgsConstructor
public class DiscountQueryRepositoryImpl implements DiscountQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Discount> findAllByPageQueryAndDiscountType(Pageable pageable, DiscountType discountType) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (discountType != null) {
            booleanBuilder.and(discount.discountType.eq(discountType));
        }

        JPQLQuery<Discount> query = queryFactory
                .selectFrom(discount)
                .where(booleanBuilder);

        return QuerydslUtil.fetchPage(query, discount, pageable);
    }

    @Override
    public List<Discount> findByGrade(DiscountType discountType) {
        return queryFactory.selectFrom(discount)
                .where(discount.discountType.eq(discountType))
                .fetch();
    }
}
