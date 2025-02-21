package flight_booking.demo.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import flight_booking.demo.domain.user.entity.User;

public class UserUtil {
	// 현재 인증된 사용자 정보를 가져오는 메서드
	public static User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		return (User)principal;
	}

	// 현재 로그인된 사용자의 ID 가져오기
	public static String getCurrentUserId() {
		User user = getCurrentUser();
		return (user != null) ? user.getId() : null;
	}

	// 현재 로그인된 사용자의 이메일 가져오기
	public static String getCurrentUserEmail() {
		User user = getCurrentUser();
		return (user != null) ? user.getEmail() : null;
	}

	// 현재 로그인된 사용자의 이름 가져오기
	public static String getCurrentUserName() {
		User user = getCurrentUser();
		return (user != null) ? user.getName() : null;
	}

	// 현재 로그인된 사용자의 권한(Role) 가져오기
	public static String getCurrentMemberShip() {
		User user = getCurrentUser();
		return (user != null) ? user.getMembership().name() : null;
	}

	public static String getCurrentRole() {
		User user = getCurrentUser();
		return (user != null) ? user.getRole().name() : null;
	}

}
