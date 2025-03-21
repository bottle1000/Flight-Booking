package flight_booking.demo.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ServerErrorResponseCode;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenProvider {

	private final JwtProperties jwtProperties;
	private final UserRepository userRepository;
	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";

	public String generateToken(User user, Duration expiredAt) {
		Date now = new Date();
		return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
	}

	//JWT 토큰 생성 메서드
	//페이로드?
	private String makeToken(Date expiry, User user) {
		Date now = new Date();
		Key secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));

		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 typ : JWT
			//내용 iss: ajufresh@gamil.com(propertise 파일에서 설정한 값)
			.setIssuer(jwtProperties.getIssuer())
			.setIssuedAt(now) //내용 iat : 현재 시간
			.setExpiration(expiry) //내용 exp : expiry 멤버 변숫값
			.setSubject(user.getEmail()) //내용 sub : 유저의 이메일
			.claim("id", user.getId()) //클레임 id : 유저 ID
			.claim("username", user.getName())
			.claim("membership", user.getMembership())
			.claim("role", user.getRole())

			// 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	//JWT 토큰 유효성 검증 메서드
	public boolean validToken(String token) {
		System.out.println("validToken으로 전달된 토큰: " + token);
		try {
			Key secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
			Jwts.parserBuilder()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//토큰 기반으로 인증 정보를 가져오는 메서드
	//authentication 객체가 session영역에 저장을 해야하고 그방법이 return해주면 됨.
	//애는 권한 있는곳에서만 작동
	//굳이 JWT토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리 때문에 sesstion넣어줌
	// 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는거임.
	public Authentication getAuthentication(String token) {
		if (token == null || token.isEmpty()) {
			return null; //  로그인하지 않았으면 null 반환
		}

		try {
			Claims claims = getClaims(token); // JWT 토큰에서 Claims(페이로드) 추출
			String id = claims.get("id", String.class);
			User findUser = userRepository.findById(id)
				.orElseThrow(() -> new CustomException(ServerErrorResponseCode.USER_NOT_FOUND));
			//  사용자 역할(Role) 설정
			Set<SimpleGrantedAuthority> authorities = Collections.singleton(
				new SimpleGrantedAuthority("ROLE_" + findUser.getRole().name())
			);

			//  SecurityContextHolder에 저장할 Authentication 객체 생성
			Authentication authentication = new UsernamePasswordAuthenticationToken(findUser, token, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication); //  SecurityContextHolder에 저장

			return authentication;
		} catch (Exception e) {
			return null; //  토큰이 유효하지 않거나 에러 발생 시 null 반환
		}
	}

	public Claims getClaims(String token) {
		Key secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public String getAccessToken(HttpServletRequest request) {
		//헤더에서 토큰을 가져온다.
		String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
			return authorizationHeader.substring(TOKEN_PREFIX.length()).trim();
		}

		//쿠키에서 토큰을 가져온다.
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("access_token".equals(cookie.getName())) {
					return cookie.getValue();
				}
				System.out.println("쿠키: " + cookie.getName());
			}
		}
		return null;
	}

	public String getRefreshToken(HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("refresh_token".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}


