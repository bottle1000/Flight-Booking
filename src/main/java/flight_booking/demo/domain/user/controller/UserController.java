package flight_booking.demo.domain.user.controller;

import flight_booking.demo.domain.user.dto.request.DeleteUserRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateMemberShipRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateRoleRequestDto;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.service.UserService;
import flight_booking.demo.security.utils.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public void updateRoleMe(@RequestBody UpdateRoleRequestDto requestDto){
        userService.updateRoleMe(requestDto);
    }
}
