package flight_booking.demo.domain.user.controller;

import flight_booking.demo.domain.order.dto.response.OrderResponseDto;
import flight_booking.demo.domain.user.dto.request.UpdateMemberShipRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateRoleRequestDto;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.service.UserService;
import flight_booking.demo.utils.PageQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<Page<User>> findUsersAll(PageQuery pageQuery){
        return ResponseEntity.ok(userService.findUserAll(pageQuery));
    }

    @PatchMapping("/role/{userId}")
    public void updateRole(@RequestBody UpdateRoleRequestDto requestDto, @PathVariable String userId){
        userService.updateRole(requestDto, userId);
    }

    @PatchMapping("/membership/{userid}")
    public void updateMemberShip(@RequestBody UpdateMemberShipRequestDto requestDto, @PathVariable String userid) {
        userService.updateMemberShip(requestDto,userid);
    }

    @PatchMapping("/membership/me")
    public void updateMemberShipMe(@RequestBody UpdateMemberShipRequestDto requestDto) {
        userService.updateMemberShipMe(requestDto);
    }

}
