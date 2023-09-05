package com.zerobase.cafebom.orders.service;

import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_NOT_COOKING_STATUS;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_NOT_CORRECT;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_NOT_EXISTS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.orders.domain.Orders;
import com.zerobase.cafebom.orders.domain.OrdersRepository;
import com.zerobase.cafebom.orders.dto.OrdersStatusModifyDto;
import com.zerobase.cafebom.security.TokenProvider;
import com.zerobase.cafebom.type.OrdersCookingStatus;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
class OrdersStatusServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenProvider tokenProvider;

    @InjectMocks
    private OrdersService ordersService;

    @Mock
    private OrdersRepository ordersRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // minsu-23.08.24
    @Test
    @DisplayName("주문 상태 변경 실패 - 다음 상태 이외의 주문으로는 변경 불가")
    void failUpdateOrdersStatusNotNone() {
        // given
        Long ordersId = 1L;
        given(ordersRepository.findById(ordersId))
            .willReturn(
                Optional.of(Orders.builder().cookingStatus(OrdersCookingStatus.COOKING).build()));

        OrdersStatusModifyDto modifyDto = OrdersStatusModifyDto.builder()
            .newStatus(OrdersCookingStatus.NONE)
            .build();

        // then
        CustomException exception = assertThrows(CustomException.class,
            () -> ordersService.modifyOrdersStatus(ordersId, modifyDto));
        assertThat(exception.getErrorCode()).isEqualTo(ORDERS_NOT_CORRECT);
    }

    // minsu-23.08.24
    @Test
    @DisplayName("주문 경과 시간 조회 실패 - 주문이 존재하지 않는 경우")
    void failGetElapsedTimeNotFound() {
        // given
        Long ordersId = 1L;

        given(ordersRepository.findById(ordersId))
            .willThrow(new CustomException(ORDERS_NOT_EXISTS));

        // then
        assertThrows(CustomException.class, () -> ordersService.getElapsedTime(ordersId));
    }

    // minsu-23.08.24
    @Test
    @DisplayName("주문 경과 시간 조회 실패 - 조리 중인 주문이 아닌 경우")
    public void failGetElapsedTimeNotCooking() {
        // given
        Long ordersId = 1L;

        given(ordersRepository.findById(ordersId))
            .willReturn(Optional.of(Orders.builder()
                .cookingStatus(OrdersCookingStatus.NONE)
                .build()));

        // then
        CustomException exception = assertThrows(CustomException.class,
            () -> ordersService.getElapsedTime(ordersId));
        assertThat(exception.getErrorCode()).isEqualTo(ORDERS_NOT_COOKING_STATUS);
    }
}