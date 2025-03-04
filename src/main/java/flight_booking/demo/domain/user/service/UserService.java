package flight_booking.demo.domain.user.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ServerErrorResponseCode;
import flight_booking.demo.domain.user.dto.request.UpdateMemberShipRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateRoleRequestDto;
import flight_booking.demo.domain.user.dto.response.FindAllUserResponseDto;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.token.entity.RefreshToken;
import flight_booking.demo.security.token.repository.RefreshTokenRepository;
import flight_booking.demo.security.utils.UserUtil;
import flight_booking.demo.utils.PageQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	public User findById(String userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new CustomException(ServerErrorResponseCode.ID_MISMATCH);
		}
		return user.get();
	}

	public void deleteUser(String userId) {
		// 사용자 조회
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ServerErrorResponseCode.USER_NOT_FOUND));
		RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId);
		refreshTokenRepository.delete(refreshToken);
		userRepository.delete(user);
	}

	public void updateRole(UpdateRoleRequestDto requestDto, String userId) {
		User findUser = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ServerErrorResponseCode.USER_NOT_FOUND));
		findUser.updateRole(requestDto.getRole());
		userRepository.save(findUser);
	}

	public void updateMemberShip(UpdateMemberShipRequestDto requestDto, String userId) {
		User findUser = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ServerErrorResponseCode.USER_NOT_FOUND));
		findUser.updateMembership(requestDto.getMemberShip());
		userRepository.save(findUser);
	}

	public void updateRoleMe(UpdateRoleRequestDto requestDto) {
		User findUser = UserUtil.getCurrentUser();
		if (findUser == null) {
			throw new CustomException(ServerErrorResponseCode.USER_NOT_FOUND);
		}
		findUser.updateRole(requestDto.getRole());
		userRepository.save(findUser);
	}

	public void updateMemberShipMe(UpdateMemberShipRequestDto requestDto) {
		User findUser = UserUtil.getCurrentUser();
		if (findUser == null) {
			throw new CustomException(ServerErrorResponseCode.USER_NOT_FOUND);
		}
		findUser.updateMembership(requestDto.getMemberShip());
		userRepository.save(findUser);
	}

	public flight_booking.demo.utils.Page<FindAllUserResponseDto> findUserAll(PageQuery pageQuery) {
		Page<User> page = userRepository.findAllByUserId(pageQuery.toPageable());

		return flight_booking.demo.utils.Page.from(page.map(FindAllUserResponseDto::from));
	}
}
