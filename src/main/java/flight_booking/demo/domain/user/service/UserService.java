package flight_booking.demo.domain.user.service;

import flight_booking.demo.common.entity.exception.CustomException;
import flight_booking.demo.common.entity.exception.ResponseCode;
import flight_booking.demo.domain.user.dto.request.UpdateMemberShipRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateRoleRequestDto;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.utils.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            throw new CustomException(ResponseCode.ID_MISMATCH);
        }
        return user.get();
    }

    public void deleteUser(String email) {
        // 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    public void updateRoleMe(UpdateRoleRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        User findUser = UserUtil.getCurrentUser();
        if (findUser == null) {
            throw new CustomException(ResponseCode.USER_NOT_FOUND);
        }
        findUser.updateRole(requestDto.getRole());
        userRepository.save(findUser);
    }

    //OWNER 전용
    public void updateRole(UpdateRoleRequestDto requestDto, String userId, HttpServletRequest request, HttpServletResponse response) {
        User findUser = UserUtil.getCurrentUser();
        if (findUser == null) {
            throw new CustomException(ResponseCode.USER_NOT_FOUND);
        }
        findUser.updateRole(requestDto.getRole());
        userRepository.save(findUser);
    }

    //OWNER 전용
    public void updateMemberShip(UpdateMemberShipRequestDto requestDto, String userId, HttpServletRequest request, HttpServletResponse response) {
        User findUser = UserUtil.getCurrentUser();
        if (findUser == null) {
            throw new CustomException(ResponseCode.USER_NOT_FOUND);
        }
        findUser.updateMembership(requestDto.getMemberShip());
        userRepository.save(findUser);
    }

    public List<User> findUserAll() {
        return userRepository.findAll();
    }
}
