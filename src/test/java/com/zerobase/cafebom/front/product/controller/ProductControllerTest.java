package com.zerobase.cafebom.front.product.controller;

import static com.zerobase.cafebom.common.exception.ErrorCode.BEST_PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH;
import static com.zerobase.cafebom.common.type.SoldOutStatus.IN_STOCK;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zerobase.cafebom.auth.service.impl.AuthService;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.product.controller.ProductController;
import com.zerobase.cafebom.front.product.domain.Option;
import com.zerobase.cafebom.front.product.domain.OptionCategory;
import com.zerobase.cafebom.front.product.domain.Product;
import com.zerobase.cafebom.front.product.dto.BestProductDto;
import com.zerobase.cafebom.front.product.dto.ProductDetailDto;
import com.zerobase.cafebom.front.product.dto.ProductDto;
import com.zerobase.cafebom.front.product.service.impl.ProductService;
import com.zerobase.cafebom.front.product.domain.ProductCategory;
import com.zerobase.cafebom.front.product.domain.ProductOptionCategory;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuthService authService;

    @MockBean
    private TokenProvider tokenProvider;

    // wooyoung-23.09.14
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
            .andExpect(jsonPath("$.productDtoList[0].productId").value("1"))
            .andExpect(jsonPath("$.productDtoList[0].name").value("아메리카노"))
            .andExpect(jsonPath("$.productDtoList[0].price").value(2000))
            .andExpect(jsonPath("$.productDtoList[0].soldOutStatus").value("IN_STOCK"))
            .andExpect(jsonPath("$.productDtoList[0].picture").value("picture"))
            .andExpect(status().isOk());
    }

    // wooyoung-23.08.29
    @Test
    @DisplayName("카테고리 별 상품 조회 실패 - 입력 타입 불일치")
    void failProductListMethodArgumentTypeMismatch() throws Exception {
        // when
        mockMvc.perform(get("/product/list/test")
                .with(csrf()))
            .andDo(print())
            .andExpect(jsonPath("$.errorCode").value(METHOD_ARGUMENT_TYPE_MISMATCH.toString()))
            .andExpect(jsonPath("$.errorMessage").value(METHOD_ARGUMENT_TYPE_MISMATCH.getMessage()))
            .andExpect(status().isBadRequest());
    }

    // wooyoung-23.09.05
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
            .andExpect(jsonPath("$.productOptionList").isMap());
    }

    // minsu-23.09.06
    @Test
    @DisplayName("베스트 상품 목록 조회 성공")
    void successBestProductList() throws Exception {
        // given
        List<BestProductDto> bestProductList = new ArrayList<>();
        bestProductList.add(BestProductDto.builder()
            .productId(1)
            .name("베스트 상품 1")
            .price(1000)
            .soldOutStatus(IN_STOCK)
            .picture("picture1")
            .build());
        bestProductList.add(BestProductDto.builder()
            .productId(2)
            .name("베스트 상품 2")
            .price(1500)
            .soldOutStatus(IN_STOCK)
            .picture("picture2")
            .build());

        given(productService.findBestProductList()).willReturn(bestProductList);

        // when
        mockMvc.perform(get("/product/best-list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.bestProducts[0].productId").value(1))
            .andExpect(jsonPath("$.bestProducts[0].name").value("베스트 상품 1"))
            .andExpect(jsonPath("$.bestProducts[0].price").value(1000))
            .andExpect(jsonPath("$.bestProducts[0].soldOutStatus").value("IN_STOCK"))
            .andExpect(jsonPath("$.bestProducts[0].picture").value("picture1"))
            .andExpect(jsonPath("$.bestProducts[1].productId").value(2))
            .andExpect(jsonPath("$.bestProducts[1].name").value("베스트 상품 2"))
            .andExpect(jsonPath("$.bestProducts[1].price").value(1500))
            .andExpect(jsonPath("$.bestProducts[1].soldOutStatus").value("IN_STOCK"))
            .andExpect(jsonPath("$.bestProducts[1].picture").value("picture2"))
            .andDo(print());
    }

    // minsu-23.09.08
    @Test
    @DisplayName("베스트 상품 목록 조회 실패 - 베스트 상품이 없는 경우")
    void failBestProductListWhenNoBestProducts() throws Exception {
        // given
        given(productService.findBestProductList()).willThrow(new CustomException(BEST_PRODUCT_NOT_EXISTS));

        // when
        mockMvc.perform(get("/product/best-list"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorCode").value(BEST_PRODUCT_NOT_EXISTS.toString()))
            .andExpect(jsonPath("$.errorMessage").value(BEST_PRODUCT_NOT_EXISTS.getMessage()))
            .andDo(print());
    }
}