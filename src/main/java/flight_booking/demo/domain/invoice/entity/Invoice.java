package flight_booking.demo.domain.invoice.entity;

import com.fasterxml.jackson.databind.JsonNode;
import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.payment.entity.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Payment payment;

    @Column(nullable = false)
    private String paymentKey;

    @Column(columnDefinition = "json")
    private String meta;

    public Invoice(JsonNode jsonNode) {
        this.paymentKey = jsonNode.get("paymentKey").asText();
        this.meta = String.valueOf(jsonNode);
    }
}