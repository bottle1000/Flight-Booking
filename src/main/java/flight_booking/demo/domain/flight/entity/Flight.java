package flight_booking.demo.domain.flight.entity;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.airplane.entity.Airplane;
import flight_booking.demo.domain.ticket.entity.Ticket;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Flight extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Airplane airplane;
}