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
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.orders.controller.form.OrdersStatusModifyForm;
import com.zerobase.cafebom.orders.service.OrdersHistoryService;
import com.zerobase.cafebom.orders.service.OrdersService;
import com.zerobase.cafebom.orders.service.dto.OrdersStatusModifyDto;
import com.zerobase.cafebom.security.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrdersController.class)
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

    @MockBean
    private MemberRepository memberRepository;

    // minsu-23.08.24
    @Test
    @DisplayName("주문 상태 변경 성공 - 정상적으로 주문 상태 변경")
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
    void successGetElapsedTime() throws Exception {
        // given
        Long ordersId = 1L;
        Long elapsedTimeMinutes = 30L;

        given(ordersService.getElapsedTime(ordersId)).willReturn(elapsedTimeMinutes);

        // then
        mockMvc.perform(get("/auth/orders-elapsed-time/{ordersId}", ordersId))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"elapsedTimeMinutes\": " + elapsedTimeMinutes + "}"))
            .andDo(print());
    }
}