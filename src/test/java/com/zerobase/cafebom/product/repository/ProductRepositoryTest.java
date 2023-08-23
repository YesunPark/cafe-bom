package com.zerobase.cafebom.product.repository;

import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import com.zerobase.cafebom.productcategory.repository.ProductCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    // wooyoung-23.08.22
    @Test
    @DisplayName("findAllByProductCategoryIdAndIsSoldOutFalse 성공")
    void successFindAllByProductCategoryIdAndIsSoldOutFalse() {
        // given
        ProductCategory coffee = ProductCategory.builder()
                .name("커피")
                .build();
        ProductCategory latte = ProductCategory.builder()
                .name("라떼")
                .build();

        productCategoryRepository.save(coffee);
        productCategoryRepository.save(latte);

        Byte[] pictureEspresso = {1, 2, 3};
        Byte[] pictureAmericano = {4, 5, 6};
        Byte[] pictureVanillaLatte = {7, 8, 9};

        Product espresso = Product.builder()
                .productCategory(coffee)
                .name("에스프레소")
                .description("씁쓸한 에스프레소")
                .price(1500)
                .isSoldOut(false)
                .picture(pictureEspresso)
                .build();
        Product americano = Product.builder()
                .productCategory(coffee)
                .name("아메리카노")
                .description("시원한 에스프레소")
                .price(2000)
                .isSoldOut(true)
                .picture(pictureAmericano)
                .build();
        Product vanillaLatte = Product.builder()
                .productCategory(latte)
                .name("바닐라라떼")
                .description("달달한 바닐라라떼")
                .price(2500)
                .isSoldOut(false)
                .picture(pictureVanillaLatte)
                .build();

        productRepository.save(espresso);
        productRepository.save(americano);
        productRepository.save(vanillaLatte);

        // when
        List<Product> productList =
                productRepository.findAllByProductCategoryIdAndIsSoldOutFalse(1);

        // then
        assertThat(productList.size()).isEqualTo(1);

        assertThat(productList.get(0).getProductCategory()).isEqualTo(espresso.getProductCategory());
        assertThat(productList.get(0).getName()).isEqualTo(espresso.getName());
        assertThat(productList.get(0).getDescription()).isEqualTo(espresso.getDescription());
        assertThat(productList.get(0).getPrice()).isEqualTo(espresso.getPrice());
        assertThat(productList.get(0).getIsSoldOut()).isEqualTo(espresso.getIsSoldOut());
        assertThat(productList.get(0).getPicture()).isEqualTo(espresso.getPicture());

    }
}