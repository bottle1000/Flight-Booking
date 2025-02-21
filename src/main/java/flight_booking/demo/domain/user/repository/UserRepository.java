package flight_booking.demo.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import flight_booking.demo.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, String>, UserQueryRepository {
	Optional<User> findByEmail(String email);
}