package com.zerobase.cafebom.orders.controller;

import static com.zerobase.cafebom.orders.domain.type.OrdersCookingStatus.COOKING;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.orders.controller.form.OrdersCookingTimeModifyForm;
import com.zerobase.cafebom.orders.controller.form.OrdersReceiptModifyForm;
import com.zerobase.cafebom.orders.controller.form.OrdersStatusModifyForm;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingTime;
import com.zerobase.cafebom.orders.domain.type.OrdersReceiptStatus;
import com.zerobase.cafebom.orders.service.OrdersService;
import com.zerobase.cafebom.orders.service.dto.OrdersStatusModifyDto;
import com.zerobase.cafebom.security.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrdersController.class)
public class OrdersStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private OrdersService ordersService;

    @Autowired
    private ObjectMapper objectMapper;

    // minsu-23.08.24
    @Test
    @DisplayName("주문 상태 변경 성공 - 정상적으로 주문 상태 변경")
    @WithMockUser(roles = "ADMIN")
    void successOrdersStatus() throws Exception {
        // given
        Long ordersId = 1L;
        OrdersStatusModifyForm form = OrdersStatusModifyForm.builder().newStatus(COOKING).build();

        // then
        mockMvc.perform(patch("/admin/orders-status/{ordersId}", ordersId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    // minsu-23.08.24
    @Test
    @DisplayName("주문 상태 변경 실패 - 주문이 존재하지 않는 경우")
    @WithMockUser(roles = "ADMIN")
    void failUpdateOrdersStatusNotFound() throws Exception {
        // given
        Long ordersId = null;
        doThrow(new CustomException(ErrorCode.ORDERS_NOT_FOUND))
            .when(ordersService).modifyOrdersStatus(ordersId,
                OrdersStatusModifyDto.builder().newStatus(COOKING).build()
            );

        // then
        mockMvc.perform(patch("/admin/orders-status/{ordersId}", ordersId))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    // minsu-23.08.24
    @Test
    @DisplayName("주문 경과 시간 조회 성공 - 주문에 대한 경과 시간 조회")
    @WithMockUser(roles = "USER")
    void successGetElapsedTime() throws Exception {
        // given
        Long ordersId = 1L;
        Long elapsedTimeMinutes = 30L;

        given(ordersService.findElapsedTime(ordersId)).willReturn(elapsedTimeMinutes);

        // then
        mockMvc.perform(get("/auth/orders-elapsed-time/{ordersId}", ordersId))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"elapsedTimeMinutes\": " + elapsedTimeMinutes + "}"))
            .andDo(print());
    }

    // minsu-23.09.01
    @Test
    @DisplayName("주문 수락 성공 - 주문을 정상적으로 수락")
    @WithMockUser(roles = "ADMIN")
    void successOrdersReceiptAccept() throws Exception {
        // given
        Long ordersId = 1L;
        OrdersReceiptModifyForm form = OrdersReceiptModifyForm.builder()
            .newReceiptStatus(OrdersReceiptStatus.RECEIVED)
            .build();

        // then
        mockMvc.perform(patch("/admin/orders-receipt-status/{ordersId}", ordersId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    // minsu-23.09.01
    @Test
    @DisplayName("주문 거절 성공 - 주문을 정상적으로 거절")
    @WithMockUser(roles = "ADMIN")
    void successOrdersReceiptReject() throws Exception {
        // given
        Long ordersId = 1L;
        OrdersReceiptModifyForm form = OrdersReceiptModifyForm.builder()
            .newReceiptStatus(OrdersReceiptStatus.REJECTED)
            .build();

        // then
        mockMvc.perform(patch("/admin/orders-receipt-status/{ordersId}", ordersId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    // minsu-23.09.01
    @Test
    @DisplayName("사용자 주문 취소 성공 - 주문을 정상적으로 취소")
    @WithMockUser(roles = "USER")
    void successUserOrdersCancel() throws Exception {
        // given
        Long ordersId = 1L;

        // then
        mockMvc.perform(patch("/auth/orders-cancel/{ordersId}", ordersId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    // minsu-23.09.01
    @Test
    @DisplayName("관리자 조리 예정 시간 선택 성공 - 주문의 조리 예정 시간을 정상적으로 선택")
    @WithMockUser(roles = "ADMIN")
    void successAdminOrdersCookingTimeModify() throws Exception {
        // given
        Long ordersId = 1L;
        OrdersCookingTimeModifyForm form = OrdersCookingTimeModifyForm.builder()
            .selectedCookingTime(OrdersCookingTime._5_TO_10_MINUTES)
            .build();

        // then
        mockMvc.perform(patch("/admin/orders-cooking-time/{ordersId}", ordersId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isNoContent());
    }
}