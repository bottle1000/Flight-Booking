package flight_booking.demo.domain.discount.controller;

import flight_booking.demo.domain.discount.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discount")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;
}
