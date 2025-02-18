package flight_booking.demo.domain.discount.entity;

import flight_booking.demo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Discount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private int rate;
    private int amount;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public Discount(
            DiscountType discountType,
            int rate,
            int amount,
            String description,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        this.discountType = discountType;
        this.rate = rate;
        this.amount = amount;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public void closeAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }
}
