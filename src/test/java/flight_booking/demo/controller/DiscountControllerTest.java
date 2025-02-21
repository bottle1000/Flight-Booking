package flight_booking.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import flight_booking.demo.BaseTest;
import flight_booking.demo.domain.discount.dto.request.DiscountCreateRequestDto;
import flight_booking.demo.domain.discount.dto.request.DiscountEndAtUpdateRequestDto;
import flight_booking.demo.domain.discount.dto.response.DiscountResponseDto;
import flight_booking.demo.domain.discount.entity.Discount;
import flight_booking.demo.domain.discount.entity.DiscountType;
import flight_booking.demo.domain.discount.repository.DiscountRepository;
import flight_booking.demo.domain.discount.service.DiscountService;
import flight_booking.demo.domain.user.entity.Role;
import mockuser.WithMockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(role = Role.ADMIN)
@SpringBootTest
@Transactional
public class DiscountControllerTest extends BaseTest {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Discount discount;
    private DiscountType discountType;

    Long id;
    int typeValue = 3; // EVENT
    int rate = 10;
    int amount = 0;
    String description = "이벤트 할인";
    LocalDateTime startAt = LocalDateTime.of(2024, 1, 1, 0, 0);
    LocalDateTime endAtForCreate = LocalDateTime.of(2999, 12, 31, 23, 59);
    LocalDateTime endAtFOrUpdate = LocalDateTime.of(2025,3,1,6,0);

    @BeforeEach
    void setUp() {
        discountType = DiscountType.of(typeValue);
        discount = new Discount(discountType, rate, amount, description, startAt, endAtForCreate);
        discount = discountRepository.save(discount);
        id = discount.getId();

        // ObjectMapper 설정 추가 (초 단위까지 변환)
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void createDiscount() throws Exception {
        // given
        DiscountCreateRequestDto createRequest = new DiscountCreateRequestDto(typeValue, rate, amount, description, startAt, endAtForCreate);
        // when
        // then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/discounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        DiscountResponseDto responseDto = objectMapper.readValue(jsonResponse, DiscountResponseDto.class);

        assertThat(responseDto.id()).isNotNull(); // ID가 null이 아님을 확인
        assertThat(responseDto.discountType()).isEqualTo(discountType);
        assertThat(responseDto.rate()).isEqualTo(rate);
        assertThat(responseDto.amount()).isEqualTo(amount);
        assertThat(responseDto.description()).isEqualTo(description);
        assertThat(responseDto.startAt()).isEqualTo(startAt);
        assertThat(responseDto.endAt()).isEqualTo(endAtForCreate);
    }

    @Test
    void updateDiscount() throws Exception {
        // given
        DiscountEndAtUpdateRequestDto updateRequest = new DiscountEndAtUpdateRequestDto(endAtFOrUpdate);
        // when
        // then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/admin/discounts/" + discount.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        DiscountResponseDto responseDto = objectMapper.readValue(jsonResponse, DiscountResponseDto.class);

        assertThat(responseDto.id()).isNotNull(); // ID가 null이 아님을 확인
        assertThat(responseDto.discountType()).isEqualTo(discountType);
        assertThat(responseDto.rate()).isEqualTo(rate);
        assertThat(responseDto.amount()).isEqualTo(amount);
        assertThat(responseDto.description()).isEqualTo(description);
        assertThat(responseDto.startAt()).isEqualTo(startAt);
        assertThat(responseDto.endAt()).isEqualTo(endAtFOrUpdate);
    }

    @Test
    void findGradeDiscount() throws Exception {
        // given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String expectedStartAt = discount.getStartAt().format(formatter);
        String expectedEndtAt = discount.getEndAt().format(formatter);
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/discounts/grade")
                        .param("discountType", discountType.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(discount.getId()))
                .andExpect(jsonPath("$.discountType").value(discount.getDiscountType().toString()))
                .andExpect(jsonPath("$.rate").value(discount.getRate()))
                .andExpect(jsonPath("$.amount").value(discount.getAmount()))
                .andExpect(jsonPath("$.description").value(discount.getDescription()))
                .andExpect(jsonPath("$.startAt").value(expectedStartAt.toString()))
                .andExpect(jsonPath("$.endAt").value(expectedEndtAt.toString()));
    }

    @Test
    void findAllDiscountByDiscountType() throws Exception {
        // given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String expectedStartAt = discount.getStartAt().format(formatter);
        String expectedEndtAt = discount.getEndAt().format(formatter);
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/discounts")
                        .param("discountType", discountType.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(discount.getId()))
                .andExpect(jsonPath("$.content[0].discountType").value(discount.getDiscountType().toString()))
                .andExpect(jsonPath("$.content[0].rate").value(discount.getRate()))
                .andExpect(jsonPath("$.content[0].amount").value(discount.getAmount()))
                .andExpect(jsonPath("$.content[0].description").value(discount.getDescription()))
                .andExpect(jsonPath("$.content[0].startAt").value(expectedStartAt.toString()))
                .andExpect(jsonPath("$.content[0].endAt").value(expectedEndtAt.toString()));
    }
}
