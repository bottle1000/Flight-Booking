package flight_booking.demo.domain.user.service;

import flight_booking.demo.common.entity.exception.ResponseCode;
import flight_booking.demo.domain.user.dto.request.UpdateMemberShipRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateRoleRequestDto;
import flight_booking.demo.domain.user.entity.Role;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //CUSTOMER 전용
    public User findById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            ResponseCode.ID_MISMATCH.getMessage();
        }
        return user.get();
    }

    public void deleteUser(String email) {
        // 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }


    //OWNER 전용
    public void updateRole(UpdateRoleRequestDto requestDto, String userId) {
        if (Role.valueOf(UserUtil.getCurrentRole()) != Role.OWNER) {
            ResponseCode.ID_MISMATCH.getMessage();
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            ResponseCode.USER_NOT_FOUND.getMessage();
        }
        User findUser = optionalUser.get();
        findUser.updateRole(requestDto.getRole());
        userRepository.save(findUser);
    }

    //OWNER 전용
    public void updateMemberShip(UpdateMemberShipRequestDto requestDto, String userId) {
        if (Role.valueOf(UserUtil.getCurrentRole()) != Role.OWNER) {
            ResponseCode.ID_MISMATCH.getMessage();
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            ResponseCode.USER_NOT_FOUND.getMessage();
        }
        User findUser = optionalUser.get();
        findUser.updateMembership(requestDto.getMemberShip());
        userRepository.save(findUser);
    }

    public List<User> findUserAll() {
        if (Role.valueOf(UserUtil.getCurrentRole()) != Role.OWNER) {
            ResponseCode.ID_MISMATCH.getMessage();
        }
        return userRepository.findAll();
    }
}
