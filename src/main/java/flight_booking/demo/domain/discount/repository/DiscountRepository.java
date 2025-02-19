package flight_booking.demo.domain.discount.repository;

import flight_booking.demo.domain.discount.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long>, DiscountQueryRepository {
}
