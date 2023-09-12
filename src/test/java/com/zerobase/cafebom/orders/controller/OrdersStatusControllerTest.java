package com.zerobase.cafebom.orders.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.orders.service.OrdersHistoryService;
import com.zerobase.cafebom.orders.service.OrdersService;
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
@WithMockUser
public class OrdersStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrdersService ordersService;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private OrdersHistoryService ordersHistoryService;

    // minsu-23.09.12
    @Test
    @DisplayName("주문 경과 시간 조회 성공 - 주문에 대한 경과 시간 조회")
    void successGetElapsedTime() throws Exception {
        // given
        Long ordersId = 1L;
        Long elapsedTimeMinutes = 30L;

        given(ordersService.findElapsedTime(ordersId)).willReturn(elapsedTimeMinutes);

        // then
        mockMvc.perform(get("/auth/orders/elapsed-time/{ordersId}", ordersId))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"elapsedTimeMinutes\": " + elapsedTimeMinutes + "}"))
            .andDo(print());
    }

    // minsu-23.09.12
    @Test
    @DisplayName("사용자 주문 취소 성공 - 주문을 정상적으로 취소")
    void successUserOrdersCancel() throws Exception {
        // given
        Long ordersId = 1L;

        // then
        mockMvc.perform(patch("/auth/orders/cancel/{ordersId}", ordersId)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
            .andExpect(status().isNoContent())
            .andDo(print());
    }
}