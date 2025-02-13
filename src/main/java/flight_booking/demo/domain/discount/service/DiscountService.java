package flight_booking.demo.domain.discount.service;

import flight_booking.demo.domain.discount.dto.request.DiscountCreateRequest;
import flight_booking.demo.domain.discount.dto.response.DiscountCreateResponse;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;
}
