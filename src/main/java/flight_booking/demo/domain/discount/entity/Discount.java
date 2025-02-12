package flight_booking.demo.domain.discount.entity;

import flight_booking.demo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Discount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private int rate;
    private int amount;
    private String description;

    private LocalDateTime start_at = null;
    private LocalDateTime end_at = null;

    public Discount(
            DiscountType discountType,
            int rate,
            int amount,
            String description) {
        //TODO: GlobalExceptionHandler 적용 이후 변경 요망
        if(rate < 0 || rate > 100) {
            throw new IllegalArgumentException("할인 비율은 0~100 사이여야 합니다.");
        }
        this.discountType = discountType;
        this.rate = rate;
        this.amount = amount;
        this.description = description;
    }
}
