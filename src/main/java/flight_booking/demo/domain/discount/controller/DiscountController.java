package flight_booking.demo.domain.discount.controller;

import flight_booking.demo.common.entity.dto.ApiResponse;
import flight_booking.demo.domain.discount.dto.request.DiscountCreateRequest;
import flight_booking.demo.domain.discount.dto.request.DiscountRateUpdateRequest;
import flight_booking.demo.domain.discount.dto.response.DiscountCreateResponse;
import flight_booking.demo.domain.discount.dto.response.DiscountRateUpdateResponse;
import flight_booking.demo.domain.discount.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discount")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<ApiResponse<DiscountCreateResponse>> createDiscountEvent(
            @RequestBody DiscountCreateRequest request
    ) {

        DiscountCreateResponse createdEvent = discountService.createEvent(request);

        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.CREATED.toString(),
                "할인 이벤트 생성 성공",
                createdEvent
        ));
    }

    @PostMapping("/rate")
    public ResponseEntity<ApiResponse<DiscountRateUpdateResponse>> updateDiscountRate(
            @RequestBody DiscountRateUpdateRequest request
    ) {
        DiscountRateUpdateResponse updatedEvent = discountService.updateDiscountRate(request);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.CREATED.toString(),
                "할인율 수정 성공",
                updatedEvent
        ));
    }
}
