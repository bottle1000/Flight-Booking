package flight_booking.demo.domain.discount.repository;

import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DiscountQueryRepository {
    Page<Discount> findAllByPageQueryAndDiscountType(Pageable pageable, DiscountType discountType);

    Optional<Discount> findByGrade(DiscountType discountType);
}
