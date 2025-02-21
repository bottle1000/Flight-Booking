package flight_booking.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Properties;

@SpringBootApplication
@EnableJpaAuditing
public class FlightBookingApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlightBookingApplication.class, args);
	}

}
