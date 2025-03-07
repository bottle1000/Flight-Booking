package flight_booking.demo.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {
    String timeout() default "5";
    String retry() default "3";
    String prefix() default "default_lock:";
    String key();
}
