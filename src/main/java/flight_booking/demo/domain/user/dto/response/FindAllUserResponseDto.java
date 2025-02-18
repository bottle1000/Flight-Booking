package flight_booking.demo.domain.user.dto.response;

import flight_booking.demo.domain.user.entity.MemberShip;
import flight_booking.demo.domain.user.entity.Role;
import flight_booking.demo.domain.user.entity.User;

public record FindAllUserResponseDto(
        String id,
        String name,
        String email,
        MemberShip membership,
        Role role
) {
    public static FindAllUserResponseDto from(User user) {
        return new FindAllUserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getMembership(),
                user.getRole()
        );
    }
}
