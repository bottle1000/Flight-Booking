package flight_booking.demo.domain.airplane.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.airplane.entity.QAirplane;
import flight_booking.demo.utils.QuerydslUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AirplaneRepositoryImpl implements AirplaneRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final QAirplane airplane = QAirplane.airplane;

	@Override
	public Page<Airplane> findAirplaneList(Pageable pageable) {
		JPQLQuery<Airplane> query = queryFactory
			.selectFrom(airplane);

		return QuerydslUtil.fetchPage(query, airplane, pageable);
	}
}
