package flight_booking.demo.domain.order.controller;

import flight_booking.demo.common.ApiResponse;
import flight_booking.demo.domain.order.dto.request.OrderCreateRequestDto;
import flight_booking.demo.domain.order.dto.request.OrderUpdateRequestDto;
import flight_booking.demo.domain.order.dto.response.OrderResponseDto;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.domain.order.service.OrderService;
import flight_booking.demo.utils.Page;
import flight_booking.demo.utils.PageQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> findAll(PageQuery pageQuery) {
        return ResponseEntity.ok(service.findAll(pageQuery));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(OrderCreateRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> update(
            @PathVariable Long orderId,
            @RequestBody OrderUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(service.update(orderId, dto));
    }
}
