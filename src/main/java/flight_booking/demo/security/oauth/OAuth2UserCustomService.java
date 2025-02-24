package flight_booking.demo.security.oauth;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import flight_booking.demo.domain.user.entity.MemberShip;
import flight_booking.demo.domain.user.entity.Role;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService { //google에 회원가입을 완료한 후 후처리
	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException { //이넘이 실행됨!!
		//userRequest는 사용자 이메일
		//구글 로그인 버튼 클릭 -> 구글로그인창 -> 로그인을 완료 -> code를 리턴(Oauth-Client라이브러리가) -> AccessToken요청
		//userRequest받음 -> 회원 프로필 받아야함(loadUser함수) -> 회원 프로필 정보 받음 구글로 부터
		OAuth2User oAuth2User = super.loadUser(userRequest); //loadUser를 통해 사용자 정보를 조회함
		saveOrUpdate(oAuth2User); // user테이블에 사용자 정보가 없다면 saveOrUpdate()메서드를 실행해 users 테이블에 회원 데이터를 추가합니다.
		return oAuth2User;
	}

	//유저가 있으면 업데이트, 없으면 유저 생성
	private User saveOrUpdate(OAuth2User oAuth2User) {

		Map<String, Object> attributes = oAuth2User.getAttributes();

		String email = (String)attributes.get("email");

		String name = (String)attributes.get("name");

		String profile_url = (String)attributes.get("picture");

		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isEmpty()) {
			User user = new User();
			user.setId(attributes.get("sub").toString());
			user.setEmail(email);
			user.setName(name);
			user.setProfile_url(profile_url);
			user.setRole(Role.CUSTOMER);
			user.setMembership(MemberShip.BASIC);
			return userRepository.save(user);
		} else {
			return optionalUser.get();
		}
	}

}
