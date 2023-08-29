package com.zerobase.cafebom.product.controller;

import static com.zerobase.cafebom.exception.ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH;
import static com.zerobase.cafebom.product.domain.entity.SoldOutStatus.IN_STOCK;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.member.service.AuthService;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.optioncategory.domain.entity.OptionCategory;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.service.ProductService;
import com.zerobase.cafebom.product.service.dto.ProductDetailDto;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import com.zerobase.cafebom.productoptioncategory.domain.entity.ProductOptionCategory;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuthService authService;

    @MockBean
    private TokenProvider tokenProvider;

    // wooyoung-23.08.26
    @Test
    @DisplayName("상품 상세 조회 성공")
    void successProductDetails() throws Exception {
        // given
        ProductCategory coffee = ProductCategory.builder()
            .id(1)
            .name("커피")
            .build();

        Product espresso = Product.builder()
            .id(1)
            .productCategory(coffee)
            .name("에스프레소")
            .description("씁쓸한 에스프레소")
            .price(1500)
            .soldOutStatus(IN_STOCK)
            .picture("picture")
            .build();

        OptionCategory size = OptionCategory.builder()
            .id(1)
            .name("사이즈")
            .build();

        ProductOptionCategory espressoSize = ProductOptionCategory.builder()
            .product(espresso)
            .optionCategory(size)
            .build();

        List<Option> optionList = new ArrayList<>();

        Option option = Option.builder()
            .optionCategory(size)
            .name("톨 사이즈")
            .price(500)
            .build();

        optionList.add(option);

        Map<ProductOptionCategory, List<Option>> productOptionList = new HashMap<>();

        productOptionList.put(espressoSize, optionList);

        ProductDetailDto productDetailDto = ProductDetailDto.builder()
            .productId(1)
            .name("에스프레소")
            .description("씁쓸한 에스프레소")
            .price(1500)
            .soldOutStatus(IN_STOCK)
            .picture("picture")
            .productOptionList(productOptionList)
            .build();

        given(productService.findProductDetails(1)).willReturn(productDetailDto);

        // when
        mockMvc.perform(get("/product/1"))
            .andDo(print())
            .andExpect(jsonPath("$.productId").value("1"))
            .andExpect(jsonPath("$.name").value("에스프레소"))
            .andExpect(jsonPath("$.description").value("씁쓸한 에스프레소"))
            .andExpect(jsonPath("$.price").value(1500))
            .andExpect(jsonPath("$.soldOutStatus").value("IN_STOCK"))
            .andExpect(jsonPath("$.picture").value("picture"))
//            .andExpect(jsonPath("$.productOptionList").value(productOptionList)) // objectMapper 공부 후 추가
            .andExpect(status().isOk());
    }

    // wooyoung-23.08.28
    @Test
    @DisplayName("상품 상세 조회 실패 - 입력 타입 불일치")
    void failProductDetailsMethodArgumentTypeMismatch() throws Exception {
        // when
        mockMvc.perform(get("/product/test"))
            .andDo(print())
            .andExpect(jsonPath("$.errorCode").value(METHOD_ARGUMENT_TYPE_MISMATCH.toString()))
            .andExpect(jsonPath("$.errorMessage").value(METHOD_ARGUMENT_TYPE_MISMATCH.getMessage()))
            .andExpect(status().isBadRequest());
    }
}