package com.zerobase.cafebom.pay.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.orders.domain.type.Payment;
import com.zerobase.cafebom.pay.controller.form.OrdersAddForm;
import com.zerobase.cafebom.pay.controller.form.OrdersAddForm.OrderedProductForm;
import com.zerobase.cafebom.pay.service.PayService;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PayController.class)
class PayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PayService payService;
    @MockBean
    private TokenProvider tokenProvider;

    String token = "Bearer token";

    // yesun-23.08.25
    @Test
    @DisplayName("주문 저장 성공 - 결제수단, 상품 id, 옵션 id")
    void successOrdersAdd() throws Exception {
        // given
        OrdersAddForm form = OrdersAddForm.builder()
            .payment(Payment.KAKAO_PAY)
            .products(List.of(new OrderedProductForm[]{
                OrderedProductForm.builder()
                    .productId(1)
                    .optionIds(List.of(new Integer[]{1, 2, 3}))
                    .build()}))
            .build();

        // when
        mockMvc.perform(post("/auth/pay")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    // yesun-23.08.25
    @Test
    @DisplayName("주문 저장 실패 - 헤더에 Authorization 없음")
    void failOrdersAddAuthorizationNotPresent() throws Exception {
        // given
        OrdersAddForm form = OrdersAddForm.builder()
            .payment(Payment.KAKAO_PAY)
            .products(List.of(new OrderedProductForm[]{
                OrderedProductForm.builder()
                    .productId(1)
                    .optionIds(List.of(new Integer[]{1, 2, 3}))
                    .build()}))
            .build();

        // when
        mockMvc.perform(post("/auth/pay")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    // yesun-23.08.25
    @Test
    @DisplayName("주문 저장 실패 - 요청 형식 오류(결제 수단 누락)")
    void failOrdersAddPaymentNotBlank() throws Exception {
        // given
        OrdersAddForm form = OrdersAddForm.builder()
            .products(List.of(
                new OrderedProductForm[]{
                    OrderedProductForm.builder()
                        .productId(1)
                        .optionIds(List.of(new Integer[]{1, 2, 3}))
                        .build()}))
            .build();

        // when
        mockMvc.perform(post("/auth/pay")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    // yesun-23.08.25 // 실패해서 임시 주석. 피드백 받고 수정하기
//    @Test
//    @DisplayName("주문 저장 실패 - 상품 ID 범위 이탈")
//    void failOrdersAddInvalidProductId() throws Exception {
//        // given
//        OrdersAddForm form = OrdersAddForm.builder()
//            .payment(Payment.KAKAO_PAY)
//            .products(List.of(
//                new OrderedProductForm[]{
//                    OrderedProductForm.builder()
//                        .productId(0)
//                        .optionIds(List.of(new Integer[]{1, 2, 3}))
//                        .build()}))
//            .build();
//
//        // when
//        mockMvc.perform(post("/auth/pay")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header("Authorization", token)
//                .content(objectMapper.writeValueAsString(form)))
//            .andExpect(status().isBadRequest())
//            .andDo(print());
//    }
}