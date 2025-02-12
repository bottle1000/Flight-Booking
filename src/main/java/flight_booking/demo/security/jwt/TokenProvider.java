package flight_booking.demo.security.jwt;

import flight_booking.demo.domain.user.entity.MemberShip;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;
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
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 typ : JWT
                //내용 iss: ajufresh@gamil.com(propertise 파일에서 설정한 값)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now) //내용 iat : 현재 시간
                .setExpiration(expiry) //내용 exp : expiry 멤버 변숫값
                .setSubject(user.getEmail()) //내용 sub : 유저의 이메일
                .claim("id", user.getId()) //클레임 id : 유저 ID
                .claim("userName", user.getName())
                .claim("membership", user.getMembership())

                // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }


    //JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        System.out.println("validToken으로 전달된 토큰: " + token);
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {//복호화 과정에서 에러가 나면 유효하지 않은 토큰
            System.out.println("잘못 만듬");
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
            String email = claims.getSubject(); // 토큰에서 이메일(subject) 추출
            String id = claims.get("id", String.class);
            String name = claims.get("userName", String.class);
            String membership = claims.get("membership", String.class); //  JWT에서 membership 가져오기

            if (email == null || email.isEmpty() || id == null || name == null || membership == null) {
                return null; //  필수 정보가 없으면 null 반환
            }

            //  JWT에서 가져온 정보로 User 객체 생성 (DB 조회 X)
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setName(name);
            user.setMembership(MemberShip.valueOf(membership)); // Enum 변환

            //  사용자 역할(Role) 설정
            Set<SimpleGrantedAuthority> authorities = Collections.singleton(
                    new SimpleGrantedAuthority("ROLE_" + membership)
            );

            //  SecurityContextHolder에 저장할 Authentication 객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, token, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication); //  SecurityContextHolder에 저장

            return authentication;
        } catch (Exception e) {
            return null; //  토큰이 유효하지 않거나 에러 발생 시 null 반환
        }
    }

    //토큰 기반으로 유저 ID를 가져오는 메서드
    public String getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", String.class);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("userName", String.class);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()// 클래임 조회
                .setSigningKey(jwtProperties.getSecretKey())
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
                System.out.println("쿠키: "+cookie.getName());
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


