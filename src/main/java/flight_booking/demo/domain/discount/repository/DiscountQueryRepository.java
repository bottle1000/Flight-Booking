package flight_booking.demo.domain.discount.repository;

import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface DiscountQueryRepository {
    Page<Discount> findAllByPageQueryAndDiscountType(Pageable pageable, DiscountType discountType);

    Set<Discount> findAllByIds(List<Long> ids);

    Discount findByGrade(DiscountType discountType);
}
