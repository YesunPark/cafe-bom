package com.zerobase.cafebom.history.controller;

import com.zerobase.cafebom.member.repository.MemberRepository;

import com.zerobase.cafebom.orders.controller.OrdersController;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.service.OrdersHistoryService;
import com.zerobase.cafebom.orders.service.dto.OrdersHisDto;
import com.zerobase.cafebom.security.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdersController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureWebMvc
public class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TokenProvider tokenProvider;

    @MockBean
    private OrdersHistoryService orderService;

    @MockBean
    private MemberRepository memberRepository;

    //youngseon 23.08.28
    @Test
    @DisplayName("모든 주문 내역을 성공적으로 조회하는 테스트")
    public void successGetAllOrderHistory() throws Exception {
        // given
        Long memberId = 1L;
        Orders orderSample = Orders.builder().build();
        OrdersHisDto orderHisDto = new OrdersHisDto();

        orderHisDto.from(orderSample);

        when(orderService.findAllOrderHistory(memberId)).thenReturn(Collections.singletonList(orderHisDto));

        // when, then
        mockMvc.perform(get("/auth/pay-list")
                        .param("memberId", String.valueOf(memberId))
                        .param("viewType", "전체"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))); // 반환된 결과는 1개여야 함
    }

    //youngseon 23.08.28
    @Test
    @DisplayName("주어진 기간 내 주문 내역을 성공적으로 조회하는 테스트")
    public void successGetOrderHistoryByPeriod() throws Exception {
        // given
        Long memberId = 1L;
        Orders orderSample = Orders.builder().build();
        OrdersHisDto orderHisDto = new OrdersHisDto();

        orderHisDto.from(orderSample);

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 31);

        when(orderService.findOrderHistoryByPeriod(memberId, startDate, endDate)).thenReturn(Collections.singletonList(orderHisDto));

        // when, then
        mockMvc.perform(get("/auth/pay-list")
                        .param("memberId", String.valueOf(memberId))
                        .param("viewType", "기간")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-03-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))); // 반환된 결과는 1개여야 함
    }

    //youngseon 23.08.28
    @Test
    @DisplayName("최근 3개월간 주문 내역을 성공적으로 조회하는 테스트")
    public void successGetOrderHistoryFor3Months() throws Exception {
        // given
        Long memberId = 1L;
        Orders orderSample = Orders.builder().build();
        OrdersHisDto orderHisDto = new OrdersHisDto();

        orderHisDto.from(orderSample);

        when(orderService.findOrderHistoryFor3Months(memberId)).thenReturn(Collections.singletonList(orderHisDto));

        // when, then
        mockMvc.perform(get("/auth/pay-list")
                        .param("memberId", String.valueOf(memberId))
                        .param("viewType", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    //youngseon 23.08.28
    @Test
    @DisplayName(" 종료 날짜 누락으로 주문 내역 조회 실패하는 테스트")
    public void failGetOrderHistoryByPeriodMissingDate() throws Exception {
        // given
        Long memberId = 1L;

        // when, then
        mockMvc.perform(get("/auth/pay-list")
                        .param("memberId", String.valueOf(memberId))
                        .param("viewType", "기간")
                        .param("startDate", "2023-01-01"))
                .andExpect(status().isNotFound());
    }
}