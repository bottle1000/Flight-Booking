package flight_booking.demo.domain.discount.service;

import flight_booking.demo.domain.discount.dto.request.DiscountCreateRequest;
import flight_booking.demo.domain.discount.dto.request.DiscountEndAtUpdateRequest;
import flight_booking.demo.domain.discount.dto.response.DiscountCreateResponse;
import flight_booking.demo.domain.discount.dto.response.DiscountEndAtResponse;
import flight_booking.demo.domain.discount.dto.response.DiscountListResponse;
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

        //TODO: GlobalExceptionHandler 적용 이후 변경 요망
        if (request.rate() < 0 || request.rate() > 100) {
            throw new IllegalArgumentException("할인 비율은 0~100 사이여야 합니다.");
        }
        if (request.amount() < 0) {
            throw new IllegalArgumentException("금액은 0 이상의 숫자여야합니다.");
        }
        if (request.started_at().isEqual(request.end_at()) || request.started_at().isAfter(request.end_at())) {
            throw new IllegalArgumentException("시작일과 종료일을 다시 설정해주세요.");
        }

        Discount newEvent = new Discount(
                request.discountType(),
                request.rate(),
                request.amount(),
                request.description(),
                request.started_at(),
                request.end_at()
        );

        Discount savedEvent = discountRepository.save(newEvent);

        return DiscountCreateResponse.from(savedEvent);
    }

    public DiscountEndAtResponse updateEndAt(Long id, DiscountEndAtUpdateRequest request) {

        Discount foundEvent = discountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "할인 정보가 없습니다."));

        foundEvent.closeAt(request.end_at());

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
