package flight_booking.demo.domain.order.repository;

import flight_booking.demo.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderQueryRepository extends JpaRepository<Order, Long> {
}
