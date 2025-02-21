package flight_booking.demo.security.token.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String userId;

	//리프레쉬 토큰이 길어서 설정해줌
	@Column(columnDefinition = "TEXT")
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
