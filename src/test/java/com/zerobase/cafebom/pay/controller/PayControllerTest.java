package com.zerobase.cafebom.pay.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.member.security.TokenProvider;
import com.zerobase.cafebom.pay.controller.form.AddOrdersForm;
import com.zerobase.cafebom.pay.controller.form.AddOrdersForm.OrderedProductForm;
import com.zerobase.cafebom.pay.service.PayService;
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

    @Test
    @DisplayName("주문 저장 성공 - 결제수단, 상품 id, 옵션 id")
    void successOrdersAdd() throws Exception {
        // given
        String token = "Bearer token";
        AddOrdersForm form = AddOrdersForm.builder()
            .payment("KAKAO_PAY")
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
}