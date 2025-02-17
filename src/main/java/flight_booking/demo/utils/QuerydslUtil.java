package flight_booking.demo.utils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class QuerydslUtil {

    private static <T> JPQLQuery<T> applyPagination(
            JPQLQuery<T> query,
            EntityPathBase<?> from,
            Pageable pageable
    ) {
        return query
                .orderBy(toOrderSpecifiers(from, pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
    }

    private static OrderSpecifier<?>[] toOrderSpecifiers(EntityPathBase<?> from, Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        sort.forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            PathBuilder<Object> path = new PathBuilder<>(Object.class, from.getMetadata());
            orderSpecifiers.add(new OrderSpecifier<>(direction, path.get(property, Comparable.class)));
        });
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    public static <T> Page<T> fetchPage(
            JPQLQuery<T> query,
            EntityPathBase<?> from,
            Pageable pageable
    ) {
        JPQLQuery<T> paginatedQuery = applyPagination(query, from, pageable);
        //Deprecated 되어 두번의 쿼리로 나눠서 실행하도록 변경
        //var results = paginatedQuery.fetchResults();
        List<T> content = paginatedQuery.fetch();
        long total = query.fetchCount();
        return new PageImpl<>(content, pageable, total);
    }
}