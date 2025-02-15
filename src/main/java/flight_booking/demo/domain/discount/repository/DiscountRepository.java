package flight_booking.demo.domain.discount.repository;

import flight_booking.demo.domain.discount.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JUNIL
 *
 * 멤버쉽 등급에 의한 할인 검색기능이 필요합니다.
 * 조건은 다음과 같습니다.
 *
 * 1. DiscountType 이 GRADE 여야 할 것.
 * 2. 검색결과가 하나여야 할 것.
 * 3. 만약 검색결과가 여러개라면 유효한 단일 검색결과만을 가져올 것.
 * 4. 3번에서의 조건 검색에도 불구하고 검색결과가 여러개라면 에러를 던질 것.
 */
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
