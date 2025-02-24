package flight_booking.demo.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import flight_booking.demo.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderQueryRepository {
}
