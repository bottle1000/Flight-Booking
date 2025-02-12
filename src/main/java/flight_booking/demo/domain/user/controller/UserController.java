package flight_booking.demo.domain.user.controller;

import flight_booking.demo.domain.user.dto.request.DeleteUserRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateUserRoleRequestDto;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.service.UserService;
import flight_booking.demo.security.jwt.TokenProvider;
import flight_booking.demo.security.utils.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @GetMapping("/email")
    public String getEmail(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/username")
    public String getUserName() {
        return UserUtil.getCurrentUserName();
    }

    @GetMapping("/id")
    public String getId() {
        return UserUtil.getCurrentUserId();
    }

    @GetMapping("/role")
    public String getRole(HttpServletRequest request) {
        return UserUtil.getCurrentUserRole();
    }

    @PatchMapping("/role")
    public String updateRole(@RequestBody UpdateUserRoleRequestDto requestDto) {
            return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@RequestBody DeleteUserRequestDto requestDto) {
        userService.deleteUser(requestDto.getEmail());
    }


}
