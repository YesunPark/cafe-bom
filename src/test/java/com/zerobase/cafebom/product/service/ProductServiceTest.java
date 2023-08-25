package com.zerobase.cafebom.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import com.zerobase.cafebom.productcategory.repository.ProductCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductService productService;

    // wooyoung-23.08.23
    @Test
    @DisplayName("findProductList 성공")
    void successFindProductList() {
        // given
        List<Product> productList = new ArrayList<>();

        ProductCategory coffee = ProductCategory.builder()
            .id(1)
            .name("커피")
            .build();
        Byte[] picture = {1, 2, 3};

        productList.add(Product.builder()
            .id(1)
            .productCategory(coffee)
            .name("아메리카노")
            .description("시원해요")
            .price(2000)
            .isSoldOut(false)
            .picture(picture)
            .build());

        given(productCategoryRepository.existsById(1)).willReturn(true);

        given(productRepository.findAllByProductCategoryIdAndIsSoldOutFalse(anyInt()))
            .willReturn(productList);

        // when
        List<ProductDto> productDtoList = productService.findProductList(1);

        // then
        assertThat(productDtoList.get(0).getProductId()).isNotNull();
        assertThat(productDtoList.get(0).getName()).isEqualTo("아메리카노");
        assertThat(productDtoList.get(0).getPrice()).isEqualTo(2000);
        assertThat(productDtoList.get(0).getPicture()).isEqualTo(picture);
    }

    // wooyoung-23.08.24
    @Test
    @DisplayName("findProductList 실패 - 존재하지 않는 상품 카테고리")
    void failFindProductListProductCategoryNotFound() {
        // given

        // when
        CustomException customException = assertThrows(CustomException.class,
            () -> productService.findProductList(1));

        // then
        assertEquals(ErrorCode.PRODUCTCATEGORY_NOT_FOUND, customException.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, customException.getErrorStatus());

    }
}