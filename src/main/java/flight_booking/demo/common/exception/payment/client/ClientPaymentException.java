package flight_booking.demo.common.exception.payment.client;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ErrorType;
import flight_booking.demo.common.exception.payment.PaymentErrorResponseCode;
import flight_booking.demo.common.exception.payment.PaymentException;
import lombok.Getter;

import static flight_booking.demo.common.exception.ServerErrorResponseCode.INVALID_ERROR_TYPE;

@Getter
public class ClientPaymentException extends RuntimeException {
    private final ClientPaymentErrorResponseCode code;
    
    public ClientPaymentException(ClientPaymentErrorResponseCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
