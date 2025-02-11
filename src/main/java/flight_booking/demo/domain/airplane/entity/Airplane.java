package flight_booking.demo.domain.airplane.entity;

import java.util.ArrayList;
import java.util.List;

import flight_booking.demo.common.entity.BaseEntity;
import flight_booking.demo.domain.flight.entity.FlightPlan;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Airplane extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@OneToMany(mappedBy = "airplane")
	private List<Seat> seatList = new ArrayList<>();

	@OneToMany(mappedBy = "airplane")
	private List<FlightPlan> flightPlanList = new ArrayList<>();

	@Builder
	public Airplane(String name) {
		this.name = name;
	}

}