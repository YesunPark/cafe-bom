package com.zerobase.cafebom.product.service;

import static com.zerobase.cafebom.exception.ErrorCode.PRODUCTCATEGORY_NOT_FOUND;
import static com.zerobase.cafebom.product.domain.type.SoldOutStatus.IN_STOCK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.zerobase.cafebom.exception.CustomException;
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

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductService productService;

    // wooyoung-23.08.29
    @Test
    @DisplayName("ProductDto 리스트 가져오기 성공")
    void successFindProductList() {
        // given
        List<Product> productList = new ArrayList<>();

        ProductCategory coffee = ProductCategory.builder()
            .id(1)
            .name("커피")
            .build();

        productList.add(Product.builder()
            .id(1)
            .productCategory(coffee)
            .name("아메리카노")
            .description("시원해요")
            .price(2000)
            .soldOutStatus(IN_STOCK)
            .picture("picture")
            .build());

        given(productCategoryRepository.existsById(1)).willReturn(true);

        given(productRepository.findAllByProductCategoryId(1))
            .willReturn(productList);

        // when
        List<ProductDto> productDtoList = productService.findProductList(1);

        // then
        assertThat(productDtoList.get(0).getProductId()).isNotNull();
        assertThat(productDtoList.get(0).getName()).isEqualTo("아메리카노");
        assertThat(productDtoList.get(0).getPrice()).isEqualTo(2000);
        assertThat(productDtoList.get(0).getPicture()).isEqualTo("picture");
    }

    // wooyoung-23.08.29
    @Test
    @DisplayName("ProductDto 리스트 가져오기 실패 - 존재하지 않는 상품 카테고리")
    void failFindProductListProductCategoryNotFound() {
        // when
        assertThatThrownBy(() -> productService.findProductList(1))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(PRODUCTCATEGORY_NOT_FOUND.getMessage());

    }
}