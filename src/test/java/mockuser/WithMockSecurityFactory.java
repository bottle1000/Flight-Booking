package mockuser;

import flight_booking.demo.domain.user.entity.MemberShip;
import flight_booking.demo.domain.user.entity.Role;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.security.jwt.TokenProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;

class WithMockSecurityFactory implements WithSecurityContextFactory<WithMockUser> {
    private final TokenProvider tokenProvider;

    public WithMockSecurityFactory(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = new User(
                annotation.id(),
                annotation.name(),
                annotation.email(),
                "Mockup profile url",
                annotation.password(),
                annotation.membership(),
                annotation.role()
        );
        user.setRole(annotation.role());

        String token = tokenProvider.generateToken(user, Duration.ofDays(1));
        Set<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_" + annotation.role().name()));
        context.setAuthentication(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(user, token, authorities));

        return context;
    }
}
