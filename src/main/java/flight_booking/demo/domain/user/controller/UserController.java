package flight_booking.demo.domain.user.controller;

import flight_booking.demo.domain.user.dto.request.DeleteUserRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateBlackListRequestDto;
import flight_booking.demo.domain.user.service.UserService;
import flight_booking.demo.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final TokenProvider tokenProvider;

    @GetMapping("/email")
    public String getEmail(Principal principal) {

        return principal.getName();
    }

    @GetMapping("/username")
    public String getUserName(HttpServletRequest request) {
       String accessToken = tokenProvider.getAccessToken(request);
        return tokenProvider.getUsernameFromToken(accessToken);
    }

    @GetMapping("/id")
    public String getId(HttpServletRequest request) {
        String accessToken =   tokenProvider.getAccessToken(request);
        return tokenProvider.getUserId(accessToken);
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
