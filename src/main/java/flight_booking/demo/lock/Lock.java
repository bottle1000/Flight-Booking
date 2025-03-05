package flight_booking.demo.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {
    String timeout() default "10";
    String retry() default "5";
    String prefix() default "default_lock:";
    String key() default "dto.ticketId";
}
