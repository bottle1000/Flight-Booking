package flight_booking.demo.domain.flight.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import flight_booking.demo.domain.airplane.entity.SeatState;
import flight_booking.demo.domain.flight.dto.response.FlightPlanGetResponse;
import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.QFlightPlan;
import flight_booking.demo.domain.flight.entity.QTicket;
import flight_booking.demo.utils.QuerydslUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlightPlanRepositoryImpl implements FlightPlanRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final QFlightPlan flightPlan = QFlightPlan.flightPlan;
	private final QTicket ticket = QTicket.ticket;

	@Override
	public Page<FlightPlan> findByFilters(
		Airport departure,
		Airport arrival,
		LocalDateTime boardingAt,
		LocalDateTime landingAt,
		Pageable pageable) {

		BooleanExpression conditions = departureEq(departure)
			.and(arrivalEq(arrival))
			.and(boardingAtGoe(boardingAt))
			.and(landingAtLoe(landingAt));

		JPQLQuery<FlightPlan> query = queryFactory
			.selectFrom(flightPlan)
			.where(conditions);

		return QuerydslUtil.fetchPage(query, flightPlan, pageable);
	}

	@Override
	public List<FlightPlanGetResponse> findTicketInfoByFlightPlanId(Long flightPlanId) {

		return queryFactory
			.select(Projections.constructor(FlightPlanGetResponse.class,

				(Expression<?>)JPAExpressions.selectFrom(ticket)
					.where(ticket.flightPlan.id.eq(flightPlanId))
					.fetch(),

				JPAExpressions.select(ticket.count())
					.from(ticket)
					.where(ticket.flightPlan.id.eq(flightPlanId)
						.and(ticket.state.eq(SeatState.IDLE)))
			))
			.from(ticket)
			.where(ticket.flightPlan.id.eq(flightPlanId))
			.fetch();
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
