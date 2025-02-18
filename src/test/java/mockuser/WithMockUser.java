package mockuser;

import flight_booking.demo.domain.user.entity.MemberShip;
import flight_booking.demo.domain.user.entity.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@WithSecurityContext(factory = WithMockSecurityFactory.class)
public @interface WithMockUser {
    String id() default "123";
    String name() default "mockup name";
    String email() default "mockup@email.com";
    String password() default "mockup password";
    MemberShip membership() default MemberShip.BASIC;
    Role role() default Role.CUSTOMER;
}