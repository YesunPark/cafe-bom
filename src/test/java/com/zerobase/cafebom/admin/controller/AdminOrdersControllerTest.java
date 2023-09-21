package com.zerobase.cafebom.admin.controller;

import static com.zerobase.cafebom.common.type.OrderCookingStatus.COOKING;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.admin.order.controller.AdminOrdersController;
import com.zerobase.cafebom.admin.service.AdminOrdersService;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.common.exception.ErrorCode;
import com.zerobase.cafebom.front.order.dto.OrdersCookingTimeModifyForm;
import com.zerobase.cafebom.front.order.dto.OrdersReceiptModifyForm;
import com.zerobase.cafebom.front.order.dto.OrdersStatusModifyDto;
import com.zerobase.cafebom.front.order.dto.OrdersStatusModifyForm;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import com.zerobase.cafebom.common.type.OrderCookingTime;
import com.zerobase.cafebom.common.type.OrdersReceiptStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminOrdersController.class)
@WithMockUser(roles = "ADMIN")
public class AdminOrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminOrdersService adminOrdersService;

    @MockBean
    private TokenProvider tokenProvider;

    // minsu-23.09.12
    @Test
    @DisplayName("주문 상태 변경 성공 - 정상적으로 주문 상태 변경")
    void successOrdersStatus() throws Exception {
        // given
        Long ordersId = 1L;
        OrdersStatusModifyForm form = OrdersStatusModifyForm.builder().newStatus(COOKING).build();

        // then
        mockMvc.perform(patch("/admin/orders/status/{ordersId}", ordersId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    // minsu-23.09.12
    @Test
    @DisplayName("주문 상태 변경 실패 - 주문이 존재하지 않는 경우")
    void failUpdateOrdersStatusNotFound() throws Exception {
        // given
        Long ordersId = 1L;
        doThrow(new CustomException(ErrorCode.ORDERS_NOT_EXISTS))
            .when(adminOrdersService).modifyOrdersStatus(ordersId,
                OrdersStatusModifyDto.builder().newStatus(COOKING).build()
            );

        // then
        mockMvc.perform(patch("/admin/orders/status/{ordersId}", ordersId)
                .with(csrf()))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    // minsu-23.09.12
    @Test
    @DisplayName("주문 수락 성공 - 주문을 정상적으로 수락")
    void successOrdersReceiptAccept() throws Exception {
        // given
        Long ordersId = 1L;
        OrdersReceiptModifyForm form = OrdersReceiptModifyForm.builder()
            .newReceiptStatus(OrdersReceiptStatus.RECEIVED)
            .build();

        // then
        mockMvc.perform(patch("/admin/orders/receipt-status/{ordersId}", ordersId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    // minsu-23.09.12
    @Test
    @DisplayName("주문 거절 성공 - 주문을 정상적으로 거절")
    void successOrdersReceiptReject() throws Exception {
        // given
        Long ordersId = 1L;
        OrdersReceiptModifyForm form = OrdersReceiptModifyForm.builder()
            .newReceiptStatus(OrdersReceiptStatus.REJECTED)
            .build();

        // then
        mockMvc.perform(patch("/admin/orders/receipt-status/{ordersId}", ordersId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    // minsu-23.09.12
    @Test
    @DisplayName("관리자 조리 예정 시간 선택 성공 - 주문의 조리 예정 시간을 정상적으로 선택")
    void successAdminOrdersCookingTimeModify() throws Exception {
        // given
        Long ordersId = 1L;
        OrdersCookingTimeModifyForm form = OrdersCookingTimeModifyForm.builder()
            .selectedCookingTime(OrderCookingTime._5_TO_10_MINUTES)
            .build();

        // then
        mockMvc.perform(patch("/admin/orders/cooking-time/{ordersId}", ordersId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isNoContent());
    }
}