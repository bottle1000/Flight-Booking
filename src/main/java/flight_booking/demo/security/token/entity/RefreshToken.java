package flight_booking.demo.security.token.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userId;

  private String refreshToken;


  // 생성자: 새로운 RefreshToken 객체 생성 시 UUID로 id 생성
  public RefreshToken(String userId, String refreshToken) {
    this.userId = userId;
    this.refreshToken = refreshToken;
  }

  // refreshToken 갱신 메서드
  public RefreshToken update(String newRefreshToken) {
    this.refreshToken = newRefreshToken;
    return this;
  }
}
