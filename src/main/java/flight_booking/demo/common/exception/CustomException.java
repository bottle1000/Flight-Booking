package flight_booking.demo.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ServerErrorResponseCode serverErrorResponseCode;

    public CustomException(ServerErrorResponseCode serverErrorResponseCode) {
        super(serverErrorResponseCode.getMessage());
        this.serverErrorResponseCode = serverErrorResponseCode;
    }
}
