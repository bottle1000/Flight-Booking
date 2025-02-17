package flight_booking.demo.security.utils;

import flight_booking.demo.domain.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

public class UserUtil {
    // 현재 인증된 사용자 정보를 가져오는 메서드
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return (User) principal;
    }

}
