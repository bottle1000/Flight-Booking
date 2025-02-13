package flight_booking.demo.domain.user.service;

import flight_booking.demo.domain.user.dto.request.UpdateBlackListRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateUserRoleRequestDto;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + userId);
        }
        return user.get();
    }

    public void updateUserRole(UpdateUserRoleRequestDto requestDto){
      String userId =  UserUtil.getCurrentUserId();
        Optional<User> optionalUser  = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new IllegalArgumentException("User not find ID: "+userId);
        }
        User findUser = optionalUser.get();
        findUser.updateMembership(requestDto.getMemberShip());
        userRepository.save(findUser);
    }

    public void deleteUser(String email) {
        // 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
