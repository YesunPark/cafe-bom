package com.zerobase.cafebom.orders.service;

import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_ALREADY_COOKING_STATUS;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_NOT_COOKING_STATUS;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_NOT_EXISTS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.member.domain.Member;
import com.zerobase.cafebom.orders.domain.Orders;
import com.zerobase.cafebom.orders.domain.OrdersRepository;
import com.zerobase.cafebom.security.TokenProvider;
import com.zerobase.cafebom.type.OrdersCookingStatus;
import com.zerobase.cafebom.type.OrdersReceiptStatus;
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
class OrdersStatusServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private OrdersService ordersService;

    @Mock
    private OrdersRepository ordersRepository;

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

        given(ordersRepository.findById(ordersId))
            .willThrow(new CustomException(ORDERS_NOT_EXISTS));

        // then
        assertThrows(CustomException.class, () -> ordersService.findElapsedTime(token, ordersId));
    }

    // minsu-23.09.19
    @Test
    @DisplayName("주문 경과 시간 조회 실패 - 조리 중인 주문이 아닌 경우")
    public void failGetElapsedTimeNotCooking() {
        // given
        Long ordersId = 1L;
        given(tokenProvider.getId(token)).willReturn(member.getId());

        Orders orders = Orders.builder()
            .member(member)
            .cookingStatus(OrdersCookingStatus.NONE)
            .build();
        given(ordersRepository.findById(ordersId)).willReturn(Optional.of(orders));

        // then
        CustomException exception = assertThrows(CustomException.class,
            () -> ordersService.findElapsedTime(token, ordersId));
        assertThat(exception.getErrorCode()).isEqualTo(ORDERS_NOT_COOKING_STATUS);
    }

    // minsu-23.09.19
    @Test
    @DisplayName("사용자 주문 취소 실패 - 이미 주문이 조리 중인 경우")
    void failUserOrdersCancelAlreadyCooking() {
        // given
        Long ordersId = 1L;
        given(tokenProvider.getId(token)).willReturn(member.getId());

        Orders orders = Orders.builder()
            .member(member)
            .cookingStatus(OrdersCookingStatus.COOKING)
            .receiptStatus(OrdersReceiptStatus.RECEIVED)
            .build();
        given(ordersRepository.findById(ordersId)).willReturn(Optional.of(orders));

        // then
        CustomException exception = assertThrows(CustomException.class,
            () -> ordersService.modifyOrdersCancel(token, ordersId));
        assertThat(exception.getErrorCode()).isEqualTo(ORDERS_ALREADY_COOKING_STATUS);
    }
}