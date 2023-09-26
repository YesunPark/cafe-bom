package com.zerobase.cafebom.front.order.service;

import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_ALREADY_COOKING_STATUS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_NOT_COOKING_STATUS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_NOT_EXISTS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.zerobase.cafebom.common.config.security.TokenProvider;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.common.type.OrderCookingStatus;
import com.zerobase.cafebom.common.type.OrderReceiptStatus;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.order.domain.Order;
import com.zerobase.cafebom.front.order.domain.OrderRepository;
import com.zerobase.cafebom.front.order.service.impl.OrderService;
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
class OrderStatusServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TokenProvider tokenProvider;

    private String token = "Bearer token";

    Member member = Member.builder()
        .id(1L)
        .build();

    // minsu-23.09.19
    @Test
    @DisplayName("주문 경과 시간 조회 실패 - 주문이 존재하지 않는 경우")
    void failGetElapsedTimeNotFound() {
        // given
        Long ordersId = 1L;
        given(tokenProvider.getId(token)).willReturn(member.getId());

        given(orderRepository.findById(ordersId))
            .willThrow(new CustomException(ORDER_NOT_EXISTS));

        // then
        assertThrows(CustomException.class, () -> orderService.findElapsedTime(token, ordersId));
    }

    // minsu-23.09.19
    @Test
    @DisplayName("주문 경과 시간 조회 실패 - 조리 중인 주문이 아닌 경우")
    public void failGetElapsedTimeNotCooking() {
        // given
        Long ordersId = 1L;
        given(tokenProvider.getId(token)).willReturn(member.getId());

        Order order = Order.builder()
            .member(member)
            .cookingStatus(OrderCookingStatus.NONE)
            .build();
        given(orderRepository.findById(ordersId)).willReturn(Optional.of(order));

        // then
        CustomException exception = assertThrows(CustomException.class,
            () -> orderService.findElapsedTime(token, ordersId));
        assertThat(exception.getErrorCode()).isEqualTo(ORDER_NOT_COOKING_STATUS);
    }

    // minsu-23.09.19
    @Test
    @DisplayName("사용자 주문 취소 실패 - 이미 주문이 조리 중인 경우")
    void failUserOrdersCancelAlreadyCooking() {
        // given
        Long ordersId = 1L;
        given(tokenProvider.getId(token)).willReturn(member.getId());

        Order order = Order.builder()
            .member(member)
            .cookingStatus(OrderCookingStatus.COOKING)
            .receiptStatus(OrderReceiptStatus.RECEIVED)
            .build();
        given(orderRepository.findById(ordersId)).willReturn(Optional.of(order));

        // then
        CustomException exception = assertThrows(CustomException.class,
            () -> orderService.modifyOrderCancel(token, ordersId));
        assertThat(exception.getErrorCode()).isEqualTo(ORDER_ALREADY_COOKING_STATUS);
    }
}