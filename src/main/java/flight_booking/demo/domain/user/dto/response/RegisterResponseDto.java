package flight_booking.demo.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class RegisterResponseDto {

    private final String email;
}

