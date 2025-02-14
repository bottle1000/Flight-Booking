package flight_booking.demo.domain.discount.service;

import flight_booking.demo.domain.discount.dto.request.DiscountCreateRequest;
import flight_booking.demo.domain.discount.dto.request.DiscountEndAtUpdateRequest;
import flight_booking.demo.domain.discount.dto.request.DiscountRateUpdateRequest;
import flight_booking.demo.domain.discount.dto.response.DiscountCreateResponse;
import flight_booking.demo.domain.discount.dto.response.DiscountEndAtResponse;
import flight_booking.demo.domain.discount.dto.response.DiscountListResponse;
import flight_booking.demo.domain.discount.dto.response.DiscountRateUpdateResponse;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public DiscountEndAtResponse updateEndAt(Long id, DiscountEndAtUpdateRequest request) {

        Discount foundEvent = discountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "할인 정보가 없습니다."));

        foundEvent.from(request.end_at());

        Discount savedEvent = discountRepository.save(foundEvent);

        return DiscountEndAtResponse.from(savedEvent);
    }

    public List<DiscountListResponse> findEventToList() {

        List<DiscountListResponse> eventList = discountRepository.findAll()
                .stream()
                .map(DiscountListResponse::from)
                .toList();

        return eventList;
    }
}
