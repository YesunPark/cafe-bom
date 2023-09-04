package com.zerobase.cafebom.product.service;

import static com.zerobase.cafebom.exception.ErrorCode.PRODUCTCATEGORY_NOT_FOUND;
import static com.zerobase.cafebom.exception.ErrorCode.PRODUCT_NOT_FOUND;
import static com.zerobase.cafebom.product.domain.type.SoldOutStatus.IN_STOCK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.optioncategory.domain.entity.OptionCategory;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.product.service.dto.ProductDetailDto;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import com.zerobase.cafebom.productcategory.repository.ProductCategoryRepository;
import com.zerobase.cafebom.productoptioncategory.domain.entity.ProductOptionCategory;
import com.zerobase.cafebom.productoptioncategory.repository.ProductOptionCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ProductOptionCategoryRepository productOptionCategoryRepository;

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private ProductService productService;

    // wooyoung-23.08.26
    @Test
    @DisplayName("상품 상세 조회 성공")
    void successFindProductDetails() {
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

        given(productRepository.findById(1)).willReturn(Optional.of(espresso));

        List<ProductOptionCategory> productOptionCategoryList = new ArrayList<>();
        productOptionCategoryList.add(espressoSize);

        given(productOptionCategoryRepository.findAllByProductId(1)).willReturn(productOptionCategoryList);

        List<Option> optionList = new ArrayList<>();

        Option option = Option.builder()
            .optionCategory(size)
            .name("톨 사이즈")
            .price(500)
            .build();

        optionList.add(option);

        given(optionRepository.findAllByOptionCategoryId(
            espressoSize.getOptionCategory())).willReturn(optionList);

        // when
        ProductDetailDto productDetails = productService.findProductDetails(1);

        // then
        assertThat(productDetails.getProductId()).isEqualTo(1);
        assertThat(productDetails.getName()).isEqualTo("에스프레소");
        assertThat(productDetails.getDescription()).isEqualTo("씁쓸한 에스프레소");
        assertThat(productDetails.getPrice()).isEqualTo(1500);
        assertThat(productDetails.getSoldOutStatus()).isEqualTo(IN_STOCK);
        assertThat(productDetails.getPicture()).isEqualTo("picture");
        assertThat(productDetails.getProductOptionList().size()).isEqualTo(1);
        assertThat(productDetails.getProductOptionList().get(espressoSize)).isEqualTo(optionList);
    }

    // wooyoung-23.08.26
    @Test
    @DisplayName("상품 상세 조회 실패 - 존재하지 않는 상품")
    void failFindProductDetailsProductNotFound() {
        // when
        assertThatThrownBy(() -> productService.findProductDetails(1))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(PRODUCT_NOT_FOUND.getMessage());
    }

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