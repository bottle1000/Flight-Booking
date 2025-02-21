package flight_booking.demo.domain.user.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flight_booking.demo.domain.user.dto.request.UpdateRoleRequestDto;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.service.UserService;
import flight_booking.demo.security.utils.UserUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/me")
	public User findUserMe() {
		return UserUtil.getCurrentUser();
	}

	@DeleteMapping("/{userid}")
	public void deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
	}

	@PatchMapping("/role/me")
	public void updateRoleMe(@RequestBody UpdateRoleRequestDto requestDto) {
		userService.updateRoleMe(requestDto);
	}
}
