package flight_booking.demo.common;

import lombok.Getter;

/**
 * SINWOO
 * 해당 Util class 자체의 편의성에 대해 몇 가지 의문점을 남깁니다.
 *
 * 1.
 * 현재 의도된 에러발생은 StatusCode, Message 를 포함한 GlobalExceptionHandler 로 처리하고 있습니다.
 * 때문에 ApiResponse 를 사용하는 것은 성공했을 경우에만 사용하고 있다고 생각합니다.
 *
 * 만약 GlobalExceptionHandler 에서 ResponseEntity 객체를 사용치 않고,
 * ApiResponse 객체만을 사용하고자 한다면 성공하는 경우에도 ResponseEntity 를 사용하지 않아야 코드의 일관성이 맞춰진다 생각합니다.
 *
 * 현재의 상태를 유지한다면, 처음 말씀드린대로 성공했을 경우에만 사용하는
 *      ApiResponse 에서 boolean 형으로 성공여부를 체크할 필요가 있을까요?
 *
 * 2.
 * 1번에서 이어지는 의문점입니다.
 * 성공하는 경우만을 상정했다면, 굳이 message 를 전달할 일이 있을까요?
 * 해당 패킷은 Front-end 에서 받아 처리할 것이며,
 * 성공했는데도 직접적으로 back-end 로부터의 message 가 필요한 경우는 어떠한 시나리오에 발생할까요?
 *
 * 3.
 * 1,2번에서 이어지는 의문점입니다.
 * 만약 message 와 success 가 필요치않게 된다면,
 * ResponseEntity 만으로도 해당 클래스의 역할을 충분히 소화할 수 있다고 생각합니다.
 * ApiResponse 로 감싸준다는 것은 좋은 방향이라 생각합니다만,
 * 만약 ResponseEntity 로서는 의도하는 기능을 수행할 수 없을때 필요해진다고 생각합니다.
 *
 * 현재로서는 반대급부로,
 * ApiResponse 로 인한 과도한 매핑이 발생하고 있으며 이로 인해 가독성이 낮아지고 불필요한 데이터패킷량이 늘어난다고 생각합니다.
 * ex)
 * ResponseEntity<ApiResponse<Page<SomethingDto>>> -> 한 눈에 어떠한 타입인지 이해하기가 힘듭니다.
 * ResponseEntity<ApiResponse.success("Some message", Page<SomethingDto>)>
 *     -> ApiResponse 에서 메시지와 데이터 이외의 어떠한 데이터를 담고 있는지, 필수불가결한지 알 수 없습니다.
 */
@Getter
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // 데이터를 포함한 성공 응답
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    // 데이터 없이 메시지만 포함한 성공 응답
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null);
    }

    // 에러 응답
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
