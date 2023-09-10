package com.zerobase.cafebom.cart.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.cart.controller.form.CartAddForm;
import com.zerobase.cafebom.cart.service.CartService;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private TokenProvider tokenProvider;

    private ObjectMapper objectMapper;
    private String token = "Bearer token";

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("장바구니 수량변경,상품 삭제,장바구니 조회 성공")
    public void successCartSave() throws Exception {
        // given
        CartAddForm cartAddForm = CartAddForm.builder()
            .productId(1)
            .count(2)
            .build();
        Mockito.when(cartService.findType(Mockito.anyString(), Mockito.any(), Mockito.anyString()))
            .thenReturn(Collections.emptyList());
        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
            .post("/cart")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .param("Type", "장바구니조회")
            .content(objectMapper.writeValueAsString(cartAddForm))
        );
        // then
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
