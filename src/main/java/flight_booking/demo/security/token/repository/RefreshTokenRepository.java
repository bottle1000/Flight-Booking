package flight_booking.demo.security.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import flight_booking.demo.security.token.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	// refreshToken 필드로 검색하는 메서드
	RefreshToken findByRefreshToken(String refreshToken);

	// userId 필드로 검색하는 메서드
	RefreshToken findByUserId(String userId);
}
