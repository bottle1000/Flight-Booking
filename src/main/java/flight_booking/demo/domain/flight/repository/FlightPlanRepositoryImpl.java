package flight_booking.demo.domain.flight.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.National;
import flight_booking.demo.domain.flight.entity.QFlightPlan;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlightPlanRepositoryImpl implements FlightPlanRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final QFlightPlan flightPlan = QFlightPlan.flightPlan;

	@Override
	public Page<FlightPlan> findByFilters(National from, National to, LocalDateTime boardingAt, LocalDateTime landingAt,
		Pageable pageable) {
		List<FlightPlan> results = queryFactory
			.selectFrom(flightPlan)
			.where(
				fromEq(from),
				toEq(to),
				boardingAt != null ? flightPlan.boardingAt.goe(boardingAt) : null,
				landingAt != null ? flightPlan.landingAt.loe(landingAt) : null
			)
			.orderBy(flightPlan.createdAt.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(flightPlan.count())
			.from(flightPlan)
			.where(
				fromEq(from),
				toEq(to),
				boardingAt != null ? flightPlan.boardingAt.goe(boardingAt) : null,
				landingAt != null ? flightPlan.landingAt.loe(landingAt) : null
			);
		return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
	}

	private BooleanExpression fromEq(National from) {
		return from != null ?
			flightPlan.from.eq(from)
			: null;
	}

	private BooleanExpression toEq(National to) {
		return to != null ?
			flightPlan.from.eq(to)
			: null;
	}

}
