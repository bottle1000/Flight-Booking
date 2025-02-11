package flight_booking.demo.domain.user.service;

import flight_booking.demo.domain.user.dto.request.UpdateBlackListRequestDto;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
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

    public void updateBlackList(UpdateBlackListRequestDto requestDto) {
        Optional<User> optionalUser = userRepository.findByEmail(requestDto.getEmail());
        if (optionalUser.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        } else {
            User user = optionalUser.get();
            user.setBlacklisted(true);
            userRepository.save(user);
            ResponseEntity.ok("User blacklisted successfully");
        }
    }

    public void deleteUser(String email) {
        // 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
