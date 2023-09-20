package com.zerobase.cafebom.orders.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.front.member.domain.MemberRepository;
import com.zerobase.cafebom.front.order.controller.OrdersController;
import com.zerobase.cafebom.front.order.domain.Orders;
import com.zerobase.cafebom.front.order.dto.OrdersHisDto;
import com.zerobase.cafebom.front.order.service.impl.OrdersHistoryService;
import com.zerobase.cafebom.front.order.service.impl.OrdersService;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = OrdersController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureWebMvc
@WithMockUser
public class OrdersListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private OrdersHistoryService orderService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private OrdersService ordersService;

    private ObjectMapper objectMapper;
    private String token = "Bearer token";

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    // youngseon-23.09.12
    @Test
    @DisplayName("모든 주문 내역을 성공적으로 조회하는 테스트")
    public void successGetAllOrderHistory() throws Exception {
        // given
        given(tokenProvider.getId(token)).willReturn(1L);
        Orders orderSample = Orders.builder().build();
        OrdersHisDto orderHisDto = new OrdersHisDto(orderSample);
        when(orderService.findAllOrderHistory(1L)).thenReturn(
            Collections.singletonList(orderHisDto));

        // when, then
        mockMvc.perform(get("/auth/orders/list")
                .header("Authorization", token)
                .param("viewType", "전체")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1))); // 반환된 결과는 1개여야 함
    }

    // youngseon-23.09.12
    @Test
    @DisplayName("주어진 기간 내 주문 내역을 성공적으로 조회하는 테스트")
    public void successGetOrderHistoryByPeriod() throws Exception {
        // given
        given(tokenProvider.getId(token)).willReturn(1L);
        Orders orderSample = Orders.builder().build();
        OrdersHisDto orderHisDto = new OrdersHisDto(orderSample);
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 31);
        when(orderService.findOrderHistoryByPeriod(1L, startDate, endDate)).thenReturn(
            Collections.singletonList(orderHisDto));

        // when, then
        mockMvc.perform(get("/auth/orders/list")
                .header("Authorization", token)
                .param("viewType", "기간")
                .param("startDate", "2023-01-01")
                .param("endDate", "2023-03-31")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1))); // 반환된 결과는 1개여야 함
    }

    // youngseon-23.09.12
    @Test
    @DisplayName("최근 3개월간 주문 내역을 성공적으로 조회하는 테스트")
    public void successGetOrderHistoryFor3Months() throws Exception {
        // given
        given(tokenProvider.getId(token)).willReturn(1L);
        Orders orderSample = Orders.builder().build();
        OrdersHisDto orderHisDto = new OrdersHisDto(orderSample);
        when(orderService.findOrderHistoryFor3Months(1L)).thenReturn(
            Collections.singletonList(orderHisDto));

        // when, then
        mockMvc.perform(get("/auth/orders/list")
                .header("Authorization", token)
                .param("viewType", "")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    // youngseon-23.09.12
    @Test
    @DisplayName(" 종료 날짜 누락으로 주문 내역 조회 실패하는 테스트")
    public void failGetOrderHistoryByPeriodMissingDate() throws Exception {
        // given
        given(tokenProvider.getId(token)).willReturn(1L);

        // when, then
        mockMvc.perform(get("/auth/orders/list")
                .header("Authorization", token)
                .param("viewType", "기간")
                .param("startDate", "2023-01-01")
                .with(csrf()))
            .andExpect(status().isBadRequest());
    }
}