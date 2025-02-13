package flight_booking.demo.domain.discount.service;

import flight_booking.demo.domain.discount.dto.request.DiscountCreateRequest;
import flight_booking.demo.domain.discount.dto.request.DiscountRateUpdateRequest;
import flight_booking.demo.domain.discount.dto.response.DiscountCreateResponse;
import flight_booking.demo.domain.discount.dto.response.DiscountRateUpdateResponse;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountCreateResponse createEvent(DiscountCreateRequest request) {
        Discount discount = new Discount(
                request.discountType(),
                request.rate(),
                request.amount(),
                request.description(),
                request.started_at(),
                request.end_at()
        );
        Discount savedEvent = discountRepository.save(discount);
        return DiscountCreateResponse.from(savedEvent);
    }

    public DiscountRateUpdateResponse updateDiscountRate(DiscountRateUpdateRequest request) {
        Discount discount = new Discount(
                request.discountType(),
                request.rate(),
                request.amount(),
                request.description(),
                request.started_at(),
                request.end_at()
        );
        Discount savedEvent = discountRepository.save(discount);
        return DiscountRateUpdateResponse.from(savedEvent);
    }
}
