package flight_booking.demo.domain.discount.service;

import flight_booking.demo.common.entity.exception.CustomException;
import flight_booking.demo.common.entity.exception.ResponseCode;
import flight_booking.demo.domain.discount.dto.request.DiscountCreateRequestDto;
import flight_booking.demo.domain.discount.dto.request.DiscountEndAtUpdateRequestDto;
import flight_booking.demo.domain.discount.dto.response.DiscountResponseDto;
import flight_booking.demo.domain.discount.dto.response.DiscountListResponseDto;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

//    public void validRate(int rate) {
//        if (rate < 0 || rate > 100) {
//            throw new CustomException(ResponseCode.RATE_BAD_REQUEST);
//        }
//    }
//    public void validAmount(int amount) {
//        if (amount < 0) {
//            throw new CustomException(ResponseCode.AMOUNT_BAD_REQUEST);
//        }
//    }
    private static void validParams(int rate, int amount, LocalDateTime startAt, LocalDateTime endAt) {
        if (rate < 0 || rate > 100) {
            throw new CustomException(ResponseCode.RATE_BAD_REQUEST);
        }
        if (amount < 0) {
            throw new CustomException(ResponseCode.AMOUNT_BAD_REQUEST);
        }
        if (startAt.isEqual(endAt) || startAt.isAfter(endAt)) {
            throw new CustomException(ResponseCode.INVALID_END_AT);
        }
    }

    private static void validDate(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isEqual(endAt) || startAt.isAfter(endAt)) {
            throw new CustomException(ResponseCode.INVALID_END_AT);
        }
    }

    public DiscountResponseDto createEvent(DiscountCreateRequestDto request) {
        DiscountType discountType = DiscountType.of(request.typeValue());
        validParams(request.rate(), request.amount(), request.startAt(), request.endAt());
        Discount newEvent = new Discount(
                discountType,
                request.rate(),
                request.amount(),
                request.description(),
                request.startAt(),
                request.endAt()
        );
        Discount savedEvent = discountRepository.save(newEvent);
        return DiscountResponseDto.from(savedEvent);
    }

    public DiscountResponseDto updateEndAt(Long id, DiscountEndAtUpdateRequestDto request){
        Discount foundEvent = discountRepository.findById(id)
                .orElseThrow(() -> new CustomException(ResponseCode.DISCOUNT_NOT_FOUND));
        validDate(foundEvent.getStartAt(), request.endAt());
        foundEvent.closeAt(request.endAt());
        Discount savedEvent = discountRepository.save(foundEvent);
        return DiscountResponseDto.from(savedEvent);
    }

    public List<DiscountListResponseDto> findEventToList() {
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
        List<DiscountListResponseDto> eventList = discountRepository.findAll()
                .stream()
                .map(DiscountListResponseDto::from)
                .toList();
        return eventList;
    }
}
