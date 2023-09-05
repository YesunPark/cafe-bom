package com.zerobase.cafebom.product.controller;

import static com.zerobase.cafebom.exception.ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH;
import static com.zerobase.cafebom.type.SoldOutStatus.IN_STOCK;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.auth.service.AuthService;
import com.zerobase.cafebom.option.domain.Option;
import com.zerobase.cafebom.optioncategory.domain.OptionCategory;
import com.zerobase.cafebom.product.domain.Product;
import com.zerobase.cafebom.product.dto.ProductDetailDto;
import com.zerobase.cafebom.product.dto.ProductDto;
import com.zerobase.cafebom.product.service.ProductService;
import com.zerobase.cafebom.productcategory.domain.ProductCategory;
import com.zerobase.cafebom.productoptioncategory.domain.ProductOptionCategory;
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
            .andExpect(jsonPath("$.picture").value("picture"));
//            .andExpect(jsonPath("$.productOptionList").value(productOptionList)) // objectMapper 공부 후 추가
    }

    // wooyoung-23.08.29
    @Test
    @DisplayName("카테고리 별 상품 조회 성공")
    void successProductList() throws Exception {
        // given
        List<ProductDto> productDtoList = new ArrayList<>();

        productDtoList.add(ProductDto.builder()
            .productId(1)
            .name("아메리카노")
            .price(2000)
            .soldOutStatus(IN_STOCK)
            .picture("picture")
            .build());

        given(productService.findProductList(anyInt())).willReturn(productDtoList);

        // when
        mockMvc.perform(get("/product/list/1"))
            .andDo(print())
            .andExpect(jsonPath("$[0].productId").value("1"))
            .andExpect(jsonPath("$[0].name").value("아메리카노"))
            .andExpect(jsonPath("$[0].price").value(2000))
            .andExpect(jsonPath("$[0].soldOutStatus").value("IN_STOCK"))
            .andExpect(jsonPath("$[0].picture").value("picture"))
            .andExpect(status().isOk());
    }

    // wooyoung-23.08.29
    @Test
    @DisplayName("카테고리 별 상품 조회 실패 - 입력 타입 불일치")
    void failProductListMethodArgumentTypeMismatch() throws Exception {
        // when
        mockMvc.perform(get("/product/list/test"))
            .andDo(print())
            .andExpect(jsonPath("$.errorCode").value(METHOD_ARGUMENT_TYPE_MISMATCH.toString()))
            .andExpect(jsonPath("$.errorMessage").value(METHOD_ARGUMENT_TYPE_MISMATCH.getMessage()))
            .andExpect(status().isBadRequest());
    }
}