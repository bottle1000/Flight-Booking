package flight_booking.demo.domain.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import flight_booking.demo.domain.order.entity.Order;

public interface OrderQueryRepository {
	Page<Order> findAllByUserId(Pageable pageable, String userId);
}
