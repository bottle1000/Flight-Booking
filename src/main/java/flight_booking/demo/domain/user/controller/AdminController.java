package flight_booking.demo.domain.user.controller;

import flight_booking.demo.domain.user.dto.request.UpdateMemberShipRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateRoleRequestDto;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public List<User> findUsersAll(){
        return userService.findUserAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/role/{userId}")
    public void updateRole(@RequestBody UpdateRoleRequestDto requestDto, @PathVariable String userId){
        userService.updateRole(requestDto, userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/membership/{userid}")
    public void updateMemberShip(@RequestBody UpdateMemberShipRequestDto requestDto, @PathVariable String userid) {
        userService.updateMemberShip(requestDto,userid);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/membership/me")
    public void updateMemberShipMe(@RequestBody UpdateMemberShipRequestDto requestDto) {
        userService.updateMemberShipMe(requestDto);
    }

}
