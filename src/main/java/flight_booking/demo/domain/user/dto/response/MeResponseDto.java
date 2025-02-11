package flight_booking.demo.domain.user.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of") // 팩토리 메서드 생성
public class MeResponseDto {
    private final Long id; // final 필드
    private final String email;
    private final String name;
}
