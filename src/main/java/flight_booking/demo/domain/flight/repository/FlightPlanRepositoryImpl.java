package flight_booking.demo.domain.flight.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import flight_booking.demo.domain.airplane.entity.SeatState;
import flight_booking.demo.domain.flight.dto.response.FlightPlanGetResponse;
import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.QFlightPlan;
import flight_booking.demo.domain.flight.entity.QTicket;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlightPlanRepositoryImpl implements FlightPlanRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final QFlightPlan flightPlan = QFlightPlan.flightPlan;
	private final QTicket ticket = QTicket.ticket;

	@Override
	public Page<FlightPlan> findByFilters(Airport departure, Airport arrival, LocalDateTime boardingAt,
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


	@Override
	public List<FlightPlanGetResponse> findTicketInfoByFlightPlanId(Long flightPlanId) {
		// Projections.constructor로 DTO 생성
		return queryFactory
			.select(Projections.constructor(FlightPlanGetResponse.class,
				// Ticket 리스트 조회
				(Expression<?>)JPAExpressions.selectFrom(ticket)
					.where(ticket.flightPlan.id.eq(flightPlanId))
					.fetch(),
				// 티켓의 IDLE 상태 개수 조회
				JPAExpressions.select(ticket.count())
					.from(ticket)
					.where(ticket.flightPlan.id.eq(flightPlanId)
						.and(ticket.state.eq(SeatState.IDLE)))
			))
			.from(ticket)
			.where(ticket.flightPlan.id.eq(flightPlanId))
			.fetch(); // List 형태로 반환
	}

	private BooleanExpression departureEq(Airport departure) {
		return departure != null ? flightPlan.departure.eq(departure) : null;
	}

	private BooleanExpression arrivalEq(Airport arrival) {
		return arrival != null ? flightPlan.arrival.eq(arrival) : null;
	}

	private BooleanExpression boardingAtGoe(LocalDateTime boardingAt) {
		return boardingAt != null ? flightPlan.boardingAt.goe(boardingAt) : null;
	}

	private BooleanExpression landingAtLoe(LocalDateTime landingAt) {
		return landingAt != null ? flightPlan.landingAt.loe(landingAt) : null;
	}
}
