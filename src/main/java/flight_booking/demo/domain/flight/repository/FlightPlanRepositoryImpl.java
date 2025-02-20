package flight_booking.demo.domain.flight.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import flight_booking.demo.domain.flight.entity.Airport;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import flight_booking.demo.domain.flight.entity.QFlightPlan;
import flight_booking.demo.domain.flight.entity.QTicket;
import flight_booking.demo.domain.flight.entity.Ticket;
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
		ZonedDateTime boardingAt,
		ZonedDateTime landingAt,
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
	public List<Ticket> findTicketInfoByFlightPlanId(Long flightPlanId) {

		return queryFactory
			.selectFrom(ticket)
			.leftJoin(ticket.flightPlan).fetchJoin()
			.leftJoin(ticket.flightPlan.airplane).fetchJoin()
			.where(ticket.flightPlan.id.eq(flightPlanId))
			.fetch();
	}

	private BooleanExpression departureEq(Airport departure) {
		return departure != null ? flightPlan.departure.eq(departure) : null;
	}

	private BooleanExpression arrivalEq(Airport arrival) {
		return arrival != null ? flightPlan.arrival.eq(arrival) : null;
	}

	private BooleanExpression boardingAtGoe(ZonedDateTime boardingAt) {
		return boardingAt != null ? flightPlan.boardingAt.goe(boardingAt) : null;
	}

	private BooleanExpression landingAtLoe(ZonedDateTime landingAt) {
		return landingAt != null ? flightPlan.landingAt.loe(landingAt) : null;
	}
}
