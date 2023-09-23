package com.zerobase.cafebom.admin.controller;

import static com.zerobase.cafebom.common.type.OrderCookingStatus.COOKING;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.admin.order.controller.AdminOrderController;
import com.zerobase.cafebom.admin.service.AdminOrderService;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.common.exception.ErrorCode;
import com.zerobase.cafebom.front.order.dto.OrderCookingTimeModifyForm;
import com.zerobase.cafebom.front.order.dto.OrderReceiptModifyForm;
import com.zerobase.cafebom.front.order.dto.OrderStatusModifyDto;
import com.zerobase.cafebom.front.order.dto.OrderStatusModifyForm;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import com.zerobase.cafebom.common.type.OrderCookingTime;
import com.zerobase.cafebom.common.type.OrderReceiptStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminOrderController.class)
@WithMockUser(roles = "ADMIN")
public class AdminOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminOrderService adminOrderService;

    @MockBean
    private TokenProvider tokenProvider;

    // minsu-23.09.12
    @Test
    @DisplayName("주문 상태 변경 성공 - 정상적으로 주문 상태 변경")
    void successOrderStatus() throws Exception {
        // given
        Long orderId = 1L;
        OrderStatusModifyForm form = OrderStatusModifyForm.builder().newStatus(COOKING).build();

        // then
        mockMvc.perform(patch("/admin/order/status/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    // minsu-23.09.12
    @Test
    @DisplayName("주문 상태 변경 실패 - 주문이 존재하지 않는 경우")
    void failUpdateOrderStatusNotFound() throws Exception {
        // given
        Long orderId = 1L;
        doThrow(new CustomException(ErrorCode.ORDER_NOT_EXISTS))
            .when(adminOrderService).modifyOrderStatus(orderId,
                OrderStatusModifyDto.builder().newStatus(COOKING).build()
            );

        // then
        mockMvc.perform(patch("/admin/order/status/{orderId}", orderId)
                .with(csrf()))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    // minsu-23.09.12
    @Test
    @DisplayName("주문 수락 성공 - 주문을 정상적으로 수락")
    void successOrderReceiptAccept() throws Exception {
        // given
        Long orderId = 1L;
        OrderReceiptModifyForm form = OrderReceiptModifyForm.builder()
            .newReceiptStatus(OrderReceiptStatus.RECEIVED)
            .build();

        // then
        mockMvc.perform(patch("/admin/order/receipt-status/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    // minsu-23.09.12
    @Test
    @DisplayName("주문 거절 성공 - 주문을 정상적으로 거절")
    void successOrderReceiptReject() throws Exception {
        // given
        Long orderId = 1L;
        OrderReceiptModifyForm form = OrderReceiptModifyForm.builder()
            .newReceiptStatus(OrderReceiptStatus.REJECTED)
            .build();

        // then
        mockMvc.perform(patch("/admin/order/receipt-status/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    // minsu-23.09.12
    @Test
    @DisplayName("관리자 조리 예정 시간 선택 성공 - 주문의 조리 예정 시간을 정상적으로 선택")
    void successAdminOrderCookingTimeModify() throws Exception {
        // given
        Long orderId = 1L;
        OrderCookingTimeModifyForm form = OrderCookingTimeModifyForm.builder()
            .selectedCookingTime(OrderCookingTime._5_TO_10_MINUTES)
            .build();

        // then
        mockMvc.perform(patch("/admin/order/cooking-time/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isNoContent());
    }
}