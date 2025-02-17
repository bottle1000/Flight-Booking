package flight_booking.demo.domain.discount.controller;

import flight_booking.demo.common.ApiResponse;
import flight_booking.demo.domain.discount.dto.request.DiscountCreateRequest;
import flight_booking.demo.domain.discount.dto.request.DiscountEndAtUpdateRequest;
import flight_booking.demo.domain.discount.dto.response.DiscountCreateResponse;
import flight_booking.demo.domain.discount.dto.response.DiscountEndAtResponse;
import flight_booking.demo.domain.discount.dto.response.DiscountListResponse;
import flight_booking.demo.domain.discount.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discount")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<ApiResponse<DiscountCreateResponse>> createDiscountEvent(
            @Valid @RequestBody DiscountCreateRequest request
    ) {
        //Todo: 변수명과 메서드명 겹침 -> 어떻게 바꾸지..?
        /**
         * JUNIL
         * DiscountCreateResponse responseDto = discountService.createEvent(request);
         * createdEvent 는 Event 객체로 해석됩니다. DTO 인 경우에는 DTO 로 네이밍해주시면 좋을듯 합니다.
         */
        DiscountCreateResponse createdEvent = discountService.createEvent(request);

        return ResponseEntity.ok(ApiResponse
                .success("할인 이벤트 생성 성공", createdEvent)
        );
    }

    // Todo: discount_id or discountId -> 뭐가 맞을까...?
    /**
     * JUNIL
     * 프로젝트 코드컨벤션은 Notion 문서를 확인하시기 바랍니다.
     * ㄴ 클래스 및 파일명은 PascalCase
     * ㄴ 변수명 및 메소드명은 camelCase
     * ㄴ DB 관련 네이밍은 snake_case
     *
     * URL 에서 {} 기호는 Java 컨벤션을 따라갑니다.
     * 다른 컨트롤러에서 어떻게 사용하는지 확인바랍니다.
     */
    @PatchMapping("/{discount_id}")
    public ResponseEntity<ApiResponse<DiscountEndAtResponse>> updateEventEndAt(
            @PathVariable Long discount_id,
            @Valid @RequestBody DiscountEndAtUpdateRequest request
    ) {

        DiscountEndAtResponse updatedEventDate = discountService.updateEndAt(discount_id, request);

        return ResponseEntity.ok(ApiResponse
                .success("할인 종료일 수정 성공", updatedEventDate)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DiscountListResponse>>> findEventList() {

        List<DiscountListResponse> eventToList = discountService.findEventToList();

        return ResponseEntity.ok(ApiResponse.success("할인 목록 조회 성공",
                eventToList)
        );
    }
}