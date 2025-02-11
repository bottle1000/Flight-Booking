package flight_booking.demo.security.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

public class CookieUtil {

  //요청값(이름, 값, 만료 기간)을 바탕으로 쿠키 추가
  //요청값(이름, 값, 만료 기간)을 바탕으로 HTTP응답에 쿠키를 추가합니다.
  public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

  //쿠키의 이름을 입력받아 쿠키 삭제
  //쿠키 이름을 입력받아 쿠키를 삭제합니다. 실제로 삭제하는 방법은 없으므로 파라미터로 넘어온 키 의 쿠키를 빈값으로 바꾸고 만료시간을 0으로 설정해 쿠키가 재생성되자마자 만료 처리합니다.
  public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return;
    }

    for (Cookie cookie : cookies) {
      if (name.equals(cookie.getName())) {
        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
      }
    }
  }

  // 객체를 직렬화해 쿠키의 값으로 변환
  //객체를 직렬화해 쿠키의 값으로 들어갈 값으로 변환합니다.
  //암호화된걸 복호화함
  public static String serialize(Object obj) {
    return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj));
  }


  // 쿠키를 역직렬화해 객체로 변환
  //쿠키를 역직렬화 객체로 변환합니다.
  public static <T> T deserialize(Cookie cookie, Class<T> cls) {
    return cls.cast(
        SerializationUtils.deserialize(
            Base64.getUrlDecoder().decode(cookie.getValue())
        )
    );
  }

  public static String getCookie(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return null;
    }
    Optional<Cookie> cookie = Arrays.stream(cookies)
            .filter(c -> c.getName().equals(name))
            .findFirst();
    return cookie.map(Cookie::getValue).orElse(null);
  }
}
