package flight_booking.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;

import java.util.Properties;

import java.util.Properties;

@SpringBootApplication
@EnableJpaAuditing
@EnableRetry
@EnableCaching
public class FlightBookingApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlightBookingApplication.class, args);
	}

}
