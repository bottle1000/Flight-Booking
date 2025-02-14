package flight_booking.demo.domain.user.controller;

import flight_booking.demo.domain.user.dto.request.DeleteUserRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateMemberShipRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateRoleRequestDto;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.service.UserService;
import flight_booking.demo.security.utils.UserUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/email")
    public String findEmail() {
        return UserUtil.getCurrentUserEmail();
    }

    @GetMapping("/username")
    public String findUserName() {
        return UserUtil.getCurrentUserName();
    }

    @GetMapping("/id")
    public String findId() {
        return UserUtil.getCurrentUserId();
    }

    @GetMapping("/role")
    public String findMembership() {
        return UserUtil.getCurrentMemberShip();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@RequestBody DeleteUserRequestDto requestDto) {
        userService.deleteUser(requestDto.getEmail());
    }
    @PatchMapping("/role")
    public void updateRoleMe(@RequestBody UpdateRoleRequestDto requestDto){
        userService.updateRoleMe(requestDto);
    }
}
