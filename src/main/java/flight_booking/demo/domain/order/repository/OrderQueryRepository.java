package flight_booking.demo.domain.order.repository;

import flight_booking.demo.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderQueryRepository {
	Page<Order> findAllByUserId(Pageable pageable, String userId);
}
