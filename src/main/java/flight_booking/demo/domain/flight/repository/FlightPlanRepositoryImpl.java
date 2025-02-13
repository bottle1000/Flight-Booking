package flight_booking.demo.domain.flight.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.QFlightPlan;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlightPlanRepositoryImpl implements FlightPlanRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final QFlightPlan flightPlan = QFlightPlan.flightPlan;

	@Override
	public Page<FlightPlan> findByFilters(String departure, String arrival, LocalDateTime boardingAt,
		LocalDateTime landingAt,
		Pageable pageable) {

		BooleanExpression conditions = departureEq(departure)
			.and(arrivalEq(arrival))
			.and(boardingAtGoe(boardingAt))
			.and(landingAtLoe(landingAt));

		List<FlightPlan> results = queryFactory
			.selectFrom(flightPlan)
			.where(conditions)
			.orderBy(flightPlan.createdAt.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(flightPlan.count())
			.from(flightPlan)
			.where(conditions);
		return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
	}

	private BooleanExpression departureEq(String departure) {
		return departure != null ? flightPlan.departure.eq(Airport.valueOf(departure)) : null;
	}

	private BooleanExpression arrivalEq(String arrival) {
		return arrival != null ? flightPlan.arrival.eq(Airport.valueOf(arrival)) : null;
	}

	private BooleanExpression boardingAtGoe(LocalDateTime boardingAt) {
		return boardingAt != null ? flightPlan.boardingAt.goe(boardingAt) : null;
	}

	private BooleanExpression landingAtLoe(LocalDateTime landingAt) {
		return landingAt != null ? flightPlan.landingAt.loe(landingAt) : null;
	}
}
