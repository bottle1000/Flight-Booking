package flight_booking.demo.domain.discount.controller;

import flight_booking.demo.domain.discount.dto.request.DiscountCreateRequestDto;
import flight_booking.demo.domain.discount.dto.request.DiscountEndAtUpdateRequestDto;
import flight_booking.demo.domain.discount.dto.response.DiscountResponseDto;
import flight_booking.demo.domain.discount.dto.response.FindDiscountResponseDto;
import flight_booking.demo.domain.discount.entity.DiscountType;
import flight_booking.demo.domain.discount.service.DiscountService;
import flight_booking.demo.utils.Page;
import flight_booking.demo.utils.PageQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping("/discounts")
    public ResponseEntity<DiscountResponseDto> createDiscountEvent(
            @Valid @RequestBody DiscountCreateRequestDto request
    ) {
        return ResponseEntity.ok(discountService.createEvent(request));
    }

    @PatchMapping("/discounts/{discountId}")
    public ResponseEntity<DiscountResponseDto> updateEventEndAt(
            @PathVariable Long discountId,
            @Valid @RequestBody DiscountEndAtUpdateRequestDto request
    ) {
        return ResponseEntity.ok(discountService.updateEndAt(discountId, request));
    }

    @GetMapping("/discounts/grade")
    public ResponseEntity<FindDiscountResponseDto> findDiscount(
            @RequestParam DiscountType discountType
    ) {
        return ResponseEntity.ok(discountService.findDiscount(discountType));
    }

    @GetMapping("/discounts")
    public ResponseEntity<Page<FindDiscountResponseDto>> findAllByPageQuery(
            PageQuery pageQuery,
            @RequestParam(required = false) DiscountType discountType
    ) {
        return ResponseEntity.ok(discountService.findAllByPageQuery(pageQuery, discountType));
    }
}