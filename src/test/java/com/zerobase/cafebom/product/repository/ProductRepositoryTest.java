package com.zerobase.cafebom.product.repository;

import static com.zerobase.cafebom.product.domain.type.SoldOutStatus.IN_STOCK;
import static com.zerobase.cafebom.product.domain.type.SoldOutStatus.SOLD_OUT;
import static org.assertj.core.api.Assertions.assertThat;

import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import com.zerobase.cafebom.productcategory.repository.ProductCategoryRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    // wooyoung-23.08.29
    @Test
    @DisplayName("상품 카테고리 아이디로 상품 목록 검색하기 성공")
    void successFindAllByProductCategoryId() {
        // given
        ProductCategory coffee = ProductCategory.builder()
                .name("커피")
                .build();
        ProductCategory latte = ProductCategory.builder()
                .name("라떼")
                .build();

        productCategoryRepository.save(coffee);
        productCategoryRepository.save(latte);

        Product espresso = Product.builder()
            .productCategory(coffee)
            .name("에스프레소")
            .description("씁쓸한 에스프레소")
            .price(1500)
            .soldOutStatus(IN_STOCK)
            .picture("pictureEspresso")
            .build();
        Product americano = Product.builder()
            .productCategory(coffee)
            .name("아메리카노")
            .description("시원한 에스프레소")
            .price(2000)
            .soldOutStatus(SOLD_OUT)
            .picture("pictureAmericano")
            .build();
        Product vanillaLatte = Product.builder()
            .productCategory(latte)
            .name("바닐라라떼")
            .description("달달한 바닐라라떼")
            .price(2500)
            .soldOutStatus(IN_STOCK)
            .picture("pictureVanillaLatte")
            .build();

        productRepository.save(espresso);
        productRepository.save(americano);
        productRepository.save(vanillaLatte);

        // when
        List<Product> productList =
                productRepository.findAllByProductCategoryId(1);

        // then
        assertThat(productList.size()).isEqualTo(2);

        assertThat(productList.get(0).getProductCategory()).isEqualTo(espresso.getProductCategory());
        assertThat(productList.get(0).getName()).isEqualTo(espresso.getName());
        assertThat(productList.get(0).getDescription()).isEqualTo(espresso.getDescription());
        assertThat(productList.get(0).getPrice()).isEqualTo(espresso.getPrice());
        assertThat(productList.get(0).getSoldOutStatus()).isEqualTo(espresso.getSoldOutStatus());
        assertThat(productList.get(0).getPicture()).isEqualTo(espresso.getPicture());

    }
}