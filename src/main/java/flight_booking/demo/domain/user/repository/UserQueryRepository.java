package flight_booking.demo.domain.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import flight_booking.demo.domain.user.entity.User;

public interface UserQueryRepository {
	Page<User> findAllByUserId(Pageable pageable);
}
