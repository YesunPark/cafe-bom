package com.zerobase.cafebom.product.controller;

import static com.zerobase.cafebom.exception.ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zerobase.cafebom.member.service.AuthService;
import com.zerobase.cafebom.product.service.ProductService;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuthService authService;

    @MockBean
    private TokenProvider tokenProvider;

    // wooyoung-23.08.23
    @Test
    @DisplayName("카테고리 별 상품 조회 성공")
    void successProductList() throws Exception {
        // given
        List<ProductDto> productDtoList = new ArrayList<>();

        Byte[] picture = {1, 2, 3};

        productDtoList.add(ProductDto.builder()
            .productId(1)
            .name("아메리카노")
            .price(2000)
            .picture(picture)
            .build());

        given(productService.findProductList(anyInt())).willReturn(productDtoList);

        // when

        // then
        mockMvc.perform(get("/product-list/1"))
            .andDo(print())
            .andExpect(jsonPath("$[0].productId").value("1"))
            .andExpect(jsonPath("$[0].name").value("아메리카노"))
            .andExpect(jsonPath("$[0].price").value(2000))
            // S3 연동 후 작성
//            .andExpect(jsonPath("$[0].picture").value(picture))
            .andExpect(status().isOk());
    }

    // wooyoung-23.08.24
    @Test
    @DisplayName("카테고리 별 상품 조회 실패 - 입력 타입 불일치")
    void failProductListMethodArgumentTypeMismatch() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get("/product-list/test"))
            .andDo(print())
            .andExpect(jsonPath("$.errorCode").value(METHOD_ARGUMENT_TYPE_MISMATCH.toString()))
            .andExpect(jsonPath("$.errorMessage").value(METHOD_ARGUMENT_TYPE_MISMATCH.getMessage()))
            .andExpect(status().isBadRequest());
    }
}