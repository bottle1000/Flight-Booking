package flight_booking.demo.domain.discount.controller;

import flight_booking.demo.common.ApiResponse;
import flight_booking.demo.domain.discount.dto.request.DiscountCreateRequestDto;
import flight_booking.demo.domain.discount.dto.request.DiscountEndAtUpdateRequestDto;
import flight_booking.demo.domain.discount.dto.response.DiscountResponseDto;
import flight_booking.demo.domain.discount.dto.response.DiscountListResponseDto;
import flight_booking.demo.domain.discount.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<DiscountResponseDto>> createDiscountEvent(
            @Valid @RequestBody DiscountCreateRequestDto request
    ) {
        DiscountResponseDto createResponseDto = discountService.createEvent(request);
        return ResponseEntity.ok(ApiResponse.success("할인 이벤트 생성 성공", createResponseDto));
    }

    @PatchMapping("/admin/{discountId}")
    public ResponseEntity<ApiResponse<DiscountResponseDto>> updateEventEndAt(
            @PathVariable Long discountId,
            @Valid @RequestBody DiscountEndAtUpdateRequestDto request
    ) {
        DiscountResponseDto updateResponseDto = discountService.updateEndAt(discountId, request);
        return ResponseEntity.ok(ApiResponse.success("할인 종료일 수정 성공", updateResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DiscountListResponseDto>>> findEventList() {
        List<DiscountListResponseDto> eventToList = discountService.findEventToList();
        return ResponseEntity.ok(ApiResponse.success("할인 목록 조회 성공", eventToList));
    }
}