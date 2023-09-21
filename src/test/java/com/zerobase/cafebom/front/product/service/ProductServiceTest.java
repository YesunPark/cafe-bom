package com.zerobase.cafebom.front.product.service;

import static com.zerobase.cafebom.common.exception.ErrorCode.BEST_PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.PRODUCTCATEGORY_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.common.type.OrderReceiptStatus.RECEIVED;
import static com.zerobase.cafebom.common.type.SoldOutStatus.IN_STOCK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.order.domain.Order;
import com.zerobase.cafebom.front.product.domain.Option;
import com.zerobase.cafebom.front.product.domain.OptionRepository;
import com.zerobase.cafebom.front.product.domain.OptionCategory;
import com.zerobase.cafebom.front.order.domain.OrderRepository;
import com.zerobase.cafebom.front.order.domain.OrderProduct;
import com.zerobase.cafebom.front.order.domain.OrderProductRepository;
import com.zerobase.cafebom.front.product.domain.Product;
import com.zerobase.cafebom.front.product.domain.ProductRepository;
import com.zerobase.cafebom.front.product.dto.BestProductDto;
import com.zerobase.cafebom.front.product.dto.ProductDetailDto;
import com.zerobase.cafebom.front.product.dto.ProductDto;
import com.zerobase.cafebom.front.product.service.impl.ProductService;
import com.zerobase.cafebom.front.product.domain.ProductCategory;
import com.zerobase.cafebom.front.product.domain.ProductCategoryRepository;
import com.zerobase.cafebom.front.product.domain.ProductOptionCategory;
import com.zerobase.cafebom.front.product.domain.ProductOptionCategoryRepository;
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
    private OrderRepository orderRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ProductOptionCategoryRepository productOptionCategoryRepository;

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private ProductService productService;

    // wooyoung-23.08.29
    @Test
    @DisplayName("카테고리 별 상품 목록 조회 성공")
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
    @DisplayName("카테고리 별 상품 목록 조회 실패 - 존재하지 않는 상품 카테고리")
    void failFindProductListProductCategoryNotFound() {
        // when
        assertThatThrownBy(() -> productService.findProductList(1))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(PRODUCTCATEGORY_NOT_EXISTS.getMessage());
    }

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
    void failFindProductDetailsProductNotExists() {
        // when
        assertThatThrownBy(() -> productService.findProductDetails(1))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(PRODUCT_NOT_EXISTS.getMessage());
    }

    // minsu-23.09.06
    @Test
    @DisplayName("베스트 상품 목록 조회 성공")
    void successFindBestProductList() {
        // given
        Product bestProduct1 = Product.builder()
            .id(1)
            .name("베스트 상품 1")
            .price(1000)
            .soldOutStatus(IN_STOCK)
            .picture("picture1")
            .build();

        Product bestProduct2 = Product.builder()
            .id(2)
            .name("베스트 상품 2")
            .price(2000)
            .soldOutStatus(IN_STOCK)
            .picture("picture2")
            .build();

        List<Order> receivedOrders = new ArrayList<>();
        receivedOrders.add(Order.builder().id(1L).receiptStatus(RECEIVED).build());
        receivedOrders.add(Order.builder().id(2L).receiptStatus(RECEIVED).build());

        List<OrderProduct> orderProducts1 = new ArrayList<>();
        orderProducts1.add(OrderProduct.builder().product(bestProduct1).quantity(5).build());

        List<OrderProduct> orderProducts2 = new ArrayList<>();
        orderProducts2.add(OrderProduct.builder().product(bestProduct2).quantity(3).build());

        given(orderRepository.findByReceiptStatus(RECEIVED)).willReturn(receivedOrders);
        given(orderProductRepository.findByOrderId(1L)).willReturn(orderProducts1);
        given(orderProductRepository.findByOrderId(2L)).willReturn(orderProducts2);

        // when
        List<BestProductDto> bestProductList = productService.findBestProductList();

        // then
        assertThat(bestProductList).hasSize(2);

        assertThat(bestProductList.get(0).getName()).isEqualTo("베스트 상품 1");
        assertThat(bestProductList.get(0).getPrice()).isEqualTo(1000);
        assertThat(bestProductList.get(0).getPicture()).isEqualTo("picture1");

        assertThat(bestProductList.get(1).getName()).isEqualTo("베스트 상품 2");
        assertThat(bestProductList.get(1).getPrice()).isEqualTo(2000);
        assertThat(bestProductList.get(1).getPicture()).isEqualTo("picture2");
    }

    // minsu-23.09.06
    @Test
    @DisplayName("베스트 상품 조회 실패 - 상품이 존재하지 않는 경우")
    void failFindBestProductListNoBestProduct() {
        // given
        given(orderRepository.findByReceiptStatus(RECEIVED)).willReturn(new ArrayList<>());

        // when
        assertThatThrownBy(() -> productService.findBestProductList())
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(BEST_PRODUCT_NOT_EXISTS.getMessage());
    }
}