package com.zerobase.cafebom.history.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.member.repository.MemberRepository;

import com.zerobase.cafebom.orders.controller.OrdersController;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.service.OrdersHistoryService;
import com.zerobase.cafebom.orders.service.dto.OrdersHisDto;
import com.zerobase.cafebom.security.TokenProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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

    @MockBean // 추가
    TokenProvider tokenProvider;

    @MockBean
    private OrdersHistoryService orderService;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    public void successGetAllOrderHistory() throws Exception {
        // Given
        Long memberId = 1L;
        Orders orderSample = Orders.builder().build();
        OrdersHisDto orderHisDto = new OrdersHisDto(orderSample);

        when(orderService.findAllOrderHistory(memberId)).thenReturn(Collections.singletonList(orderHisDto));

        // When, Then
        mockMvc.perform(get("/auth/pay-list")
                        .param("memberId", String.valueOf(memberId))
                        .param("viewType", "전체"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))); // 반환된 결과는 1개여야 함
    }

    @Test
    public void successGetOrderHistoryByPeriod() throws Exception {
        // Given
        Long memberId = 1L;
        Orders orderSample = Orders.builder().build();
        OrdersHisDto orderHisDto = new OrdersHisDto(orderSample);

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 31);

        when(orderService.findOrderHistoryByPeriod(memberId, startDate, endDate)).thenReturn(Collections.singletonList(orderHisDto));

        // When, Then
        mockMvc.perform(get("/auth/pay-list")
                        .param("memberId", String.valueOf(memberId))
                        .param("viewType", "기간")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-03-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))); // 반환된 결과는 1개여야 함
    }

    @Test
    public void successGetOrderHistoryFor3Months() throws Exception {
        // Given
        Long memberId = 1L;
        Orders orderSample = Orders.builder().build();
        OrdersHisDto orderHisDto = new OrdersHisDto(orderSample);

        when(orderService.findOrderHistoryFor3Months(memberId)).thenReturn(Collections.singletonList(orderHisDto));

        // When, Then
        mockMvc.perform(get("/auth/pay-list")
                        .param("memberId", String.valueOf(memberId))
                        .param("viewType", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void failGetOrderHistoryByPeriodMissingDate() throws Exception {
        // Given
        Long memberId = 1L;

        // When, Then
        mockMvc.perform(get("/auth/pay-list")
                        .param("memberId", String.valueOf(memberId))
                        .param("viewType", "기간")
                        .param("startDate", "2023-01-01"))
                .andExpect(status().isNotFound());
    }
}