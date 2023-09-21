package com.zerobase.cafebom.front.order.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.front.member.domain.MemberRepository;
import com.zerobase.cafebom.front.order.dto.OrderAddForm;
import com.zerobase.cafebom.front.order.service.impl.OrderHistoryService;
import com.zerobase.cafebom.front.order.service.impl.OrderService;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import com.zerobase.cafebom.common.type.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
@WithMockUser
class OrderAddControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;
    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private OrderHistoryService orderHistoryService;

    @MockBean
    private MemberRepository memberRepository;

    String token = "Bearer token";

    OrderAddForm.Request form;

    // yesun-23.08.31
    @BeforeEach
    public void setUp() {
        // given
        form = OrderAddForm.Request.builder()
            .payment(Payment.KAKAO_PAY)
            .build();
    }

    // yesun-23.09.12
    @Test
    @DisplayName("주문 저장 성공 - 토큰, 결제 수단을 받아 주문 저장")
    void successOrdersAdd() throws Exception {
        // when
        mockMvc.perform(post("/auth/orders")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().is(201))
            .andDo(print());
    }

    // yesun-23.09.12
    @Test
    @DisplayName("주문 저장 실패 - 헤더에 Authorization 없음")
    void failOrdersAddAuthorizationNotPresent() throws Exception {
        // when
        mockMvc.perform(post("/auth/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    // yesun-23.09.12
    @Test
    @DisplayName("주문 저장 실패 - 요청 형식 오류(결제 수단 누락)")
    void failOrdersAddPaymentNotBlank() throws Exception {
        // given
        OrderAddForm.Request form = OrderAddForm.Request.builder()
            .build();

        // when
        mockMvc.perform(post("/auth/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }
}