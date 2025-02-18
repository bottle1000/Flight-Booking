package flight_booking.demo.domain.user.repository;

import flight_booking.demo.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserQueryRepository {
        Page<User> findAllByUserId(Pageable pageable);
}
