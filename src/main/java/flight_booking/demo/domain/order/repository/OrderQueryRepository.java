package flight_booking.demo.domain.order.repository;

import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.utils.PageQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderQueryRepository {
    Page<Order> findAllByUserId(Pageable pageable, String userId);
}
