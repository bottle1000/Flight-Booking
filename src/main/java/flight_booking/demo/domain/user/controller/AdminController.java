package flight_booking.demo.domain.user.controller;

import flight_booking.demo.domain.user.dto.request.UpdateMemberShipRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateRoleRequestDto;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    @GetMapping("/")
    public List<User> findUsersAll(){
        return userService.findUserAll();
    }
    @PatchMapping("/role/{userId}")
    public void updateRole(@RequestBody UpdateRoleRequestDto requestDto, @PathVariable String userId){
        userService.updateRole(requestDto, userId);
    }
    @PatchMapping("/membership/{userId}")
    public void updateMemberShip(@RequestBody UpdateMemberShipRequestDto requestDto, @PathVariable String userId) {
        userService.updateMemberShip(requestDto,userId);
    }
}
