package flight_booking.demo.security.jwt;

import flight_booking.demo.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

  private final JwtProperties jwtProperties;

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
        // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
        .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
        .compact();
  }


  //JWT 토큰 유효성 검증 메서드
  public boolean validToken(String token) {
    try {
      Jwts.parser()
          .setSigningKey(jwtProperties.getSecretKey())
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {//복호화 과정에서 에러가 나면 유효하지 않은 토큰
      return false;
    }
  }

  //토큰 기반으로 인증 정보를 가져오는 메서드
  //authentication 객체가 session영역에 저장을 해야하고 그방법이 return해주면 됨.
  //애는 권한 있는곳에서만 작동
  //굳이 JWT토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리 때문에 sesstion넣어줌
  // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는거임.
    public Authentication getAuthentication(String token){

      Claims claims = getClaims(token);
      Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    //JWT토큰 서명을 통해서 서명이 정상이면 Authentication객체를 만들어준다.
      return  new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }

  //토큰 기반으로 유저 ID를 가져오는 메서드
    public  String getUserId(String token){
      Claims claims = getClaims(token);
      return claims.get("id",String.class);
    }

  public String getUsernameFromToken(String token) {
    Claims claims = getClaims(token);
    return claims.getSubject();
  }

    private Claims getClaims(String token){
      return Jwts.parser()// 클래임 조회
          .setSigningKey(jwtProperties.getSecretKey())
          .parseClaimsJws(token)
          .getBody();
    }
}


