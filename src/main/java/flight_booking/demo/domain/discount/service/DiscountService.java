package flight_booking.demo.domain.discount.service;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ResponseCode;
import flight_booking.demo.domain.discount.dto.request.DiscountCreateRequestDto;
import flight_booking.demo.domain.discount.dto.request.DiscountEndAtUpdateRequestDto;
import flight_booking.demo.domain.discount.dto.response.DiscountResponseDto;
import flight_booking.demo.domain.discount.dto.response.FindDiscountResponseDto;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import flight_booking.demo.utils.Page;
import flight_booking.demo.utils.PageQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    // 생성시 requestDto 검증 -> 예외처리
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

    // 종료일 수정 시 requestDto 검증 -> 예외처리
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

    public Page<FindDiscountResponseDto> findAllByPageQuery(PageQuery pageQuery, DiscountType discountType) {
        org.springframework.data.domain.Page<Discount> page =
                discountRepository.findAllByPageQueryAndDiscountType(pageQuery.toPageable(), discountType);
        return Page.from(page.map(FindDiscountResponseDto::from));
    }

    public FindDiscountResponseDto findDiscount(DiscountType discountType) {
        Discount gradeDiscount = discountRepository.findByGrade(discountType)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBERSHIP_DISCOUNT_NOT_FOUND));
        return FindDiscountResponseDto.from(gradeDiscount);
    }
}
