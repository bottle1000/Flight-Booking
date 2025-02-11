package flight_booking.demo.domain.user.controller;

import flight_booking.demo.domain.user.dto.request.DeleteUserRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateBlackListRequestDto;
import flight_booking.demo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    public ResponseEntity<?> getMe(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", principal.getName());

        return ResponseEntity.ok(userInfo);
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@RequestBody DeleteUserRequestDto requestDto) {
        userService.deleteUser(requestDto.getEmail());
    }

    @PatchMapping("/black")
    public void updateBlackList(@RequestBody UpdateBlackListRequestDto requestDto){
        userService.updateBlackList(requestDto);
    }

}
