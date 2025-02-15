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

        /**
         * TODO: GlobalExceptionHandler 적용 이후 변경 요망
         * JUNIL
         * 1.
         * 다중 if 문은 모두 RequestDto 의 Valid 를 확인하는 것으로 보입니다.
         * 해당 부분은 method 로 분리하여 가독성을 높이면 좋을듯 합니다.
         *
         * 2.
         * 시작일과 종료일의 에러메시지가 명확하지 않습니다.
         * 어째서 다시 설정해야하는지 메시지에 기재하여 주십시오.
         *
         * 3.
         * 무분별한 줄들임이 많이 사용되고 있습니다.
         * 코드의 문맥을 기준으로 줄들임을 적용시키면 가독성이 올라갈 것으로 생각됩니다.
         */
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
        /**
         * JUNIL
         * 1.
         * JPA 의 findAll() 메소드는 말그대로 "모든 Entity" 를 가져옵니다.
         * 필요없는 Entity 까지 가져오므로, 필요한 것들만 선택하여 가져오는 쿼리를 작성하심이 좋을듯 합니다.
         *
         * 2.
         * findEventToList() 라는 메소드명은 추후 수정될 가능성이 높아보입니다.
         * 단일 객체가 아닌 결과는 Page<T> 형태로 반환해야 하므로, findAllBy~~ 형식의 메소드명이 좋아보입니다.
         * 어떤 조건에 의해서 어떤 결과값이 나오는지 명확히 이해되지 않는다면 "팀장과의 회의"를 통하여 정함이 옳게 보입니다.
         */
        List<DiscountListResponse> eventList = discountRepository.findAll()
                .stream()
                .map(DiscountListResponse::from)
                .toList();

        return eventList;
    }
}
