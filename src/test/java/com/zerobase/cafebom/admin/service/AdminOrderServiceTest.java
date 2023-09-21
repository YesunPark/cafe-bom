package com.zerobase.cafebom.admin.service;

import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_ALREADY_CANCELED;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_COOKING_TIME_ALREADY_SET;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_NOT_RECEIVED_STATUS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_STATUS_ONLY_NEXT;
import static com.zerobase.cafebom.common.type.OrderCookingStatus.COOKING;
import static com.zerobase.cafebom.common.type.OrderCookingTime.OVER_25_MINUTES;
import static com.zerobase.cafebom.common.type.OrderCookingTime._5_TO_10_MINUTES;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.common.type.OrderCookingStatus;
import com.zerobase.cafebom.common.type.OrderReceiptStatus;
import com.zerobase.cafebom.front.order.domain.Order;
import com.zerobase.cafebom.front.order.domain.OrderRepository;
import com.zerobase.cafebom.front.order.dto.OrderCookingTimeModifyDto;
import com.zerobase.cafebom.front.order.dto.OrderReceiptModifyDto;
import com.zerobase.cafebom.front.order.dto.OrderStatusModifyDto;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
class AdminOrderServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AdminOrderService adminOrderService;

    @Mock
    private OrderRepository orderRepository;

    // minsu-23.09.20
    @Test
    @DisplayName("주문 상태 변경 실패 - 주문 상태는 전으로 돌아갈 수 없음")
    void failUpdateOrdersStatusOnlyNext() {
        // given
        Long ordersId = 1L;
        given(orderRepository.findById(ordersId))
            .willReturn(
                Optional.of(Order.builder().cookingStatus(COOKING).build()));

        OrderStatusModifyDto modifyDto = OrderStatusModifyDto.builder()
            .newStatus(OrderCookingStatus.NONE)
            .build();

        // then
        CustomException exception = assertThrows(CustomException.class,
            () -> adminOrderService.modifyOrderStatus(ordersId, modifyDto));
        assertThat(exception.getErrorCode()).isEqualTo(ORDER_STATUS_ONLY_NEXT);
    }

    // minsu-23.09.20
    @Test
    @DisplayName("주문 수락/거절 실패 - 주문이 이미 취소된 경우")
    void failOrdersReceiptAlreadyCanceled() {
        // given
        Long ordersId = 1L;

        given(orderRepository.findById(ordersId))
            .willReturn(
                Optional.of(Order.builder().receiptStatus(OrderReceiptStatus.CANCELED).build()));

        OrderReceiptModifyDto modifyDto = OrderReceiptModifyDto.builder()
            .newReceiptStatus(OrderReceiptStatus.RECEIVED)
            .build();

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> adminOrderService.modifyOrderReceiptStatus(ordersId, modifyDto));

        // then
        assertThat(exception.getErrorCode()).isEqualTo(ORDER_ALREADY_CANCELED);
    }

    // minsu-23.09.20
    @Test
    @DisplayName("관리자 조리 예정 시간 선택 실패 - 주문이 수락되지 않은 경우")
    void failAdminOrdersCookingTimeModifyNotReceived() {
        // given
        Long ordersId = 1L;
        given(orderRepository.findById(ordersId))
            .willReturn(
                Optional.of(Order.builder().receiptStatus(OrderReceiptStatus.CANCELED).build()));

        OrderCookingTimeModifyDto modifyDto = OrderCookingTimeModifyDto.builder()
            .selectedCookingTime(_5_TO_10_MINUTES)
            .build();

        // then
        CustomException exception = assertThrows(CustomException.class,
            () -> adminOrderService.modifyOrderCookingTime(ordersId, modifyDto));
        assertThat(exception.getErrorCode()).isEqualTo(ORDER_NOT_RECEIVED_STATUS);
    }

    // minsu-23.09.20
    @Test
    @DisplayName("관리자 조리 예정 시간 선택 실패 - 이미 조리 예정 시간이 설정되어 있는 경우")
    void failAdminOrdersCookingTimeModifyAlreadySet() {
        // given
        Long ordersId = 1L;
        Order order = Order.builder()
            .id(ordersId)
            .cookingStatus(COOKING)
            .receiptStatus(OrderReceiptStatus.RECEIVED)
            .cookingTime(_5_TO_10_MINUTES) // Already set
            .receivedTime(LocalDateTime.now())
            .build();

        given(orderRepository.findById(ordersId)).willReturn(Optional.of(order));

        OrderCookingTimeModifyDto modifyDto = OrderCookingTimeModifyDto.builder()
            .selectedCookingTime(OVER_25_MINUTES)
            .build();

        // then
        CustomException exception = assertThrows(CustomException.class,
            () -> adminOrderService.modifyOrderCookingTime(ordersId, modifyDto));
        assertThat(exception.getErrorCode()).isEqualTo(ORDER_COOKING_TIME_ALREADY_SET);
    }
}