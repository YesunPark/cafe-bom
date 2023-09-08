package com.zerobase.cafebom.productoptioncategory.domain;

import static com.zerobase.cafebom.type.SoldOutStatus.IN_STOCK;
import static org.assertj.core.api.Assertions.assertThat;

import com.zerobase.cafebom.optioncategory.domain.OptionCategory;
import com.zerobase.cafebom.optioncategory.domain.OptionCategoryRepository;
import com.zerobase.cafebom.product.domain.Product;
import com.zerobase.cafebom.product.domain.ProductRepository;
import com.zerobase.cafebom.productcategory.domain.ProductCategory;
import com.zerobase.cafebom.productcategory.domain.ProductCategoryRepository;
import com.zerobase.cafebom.productoptioncategory.domain.ProductOptionCategory;
import com.zerobase.cafebom.productoptioncategory.domain.ProductOptionCategoryRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductOptionCategoryRepositoryTest {

    @Autowired
    private ProductOptionCategoryRepository productOptionCategoryRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OptionCategoryRepository optionCategoryRepository;

    // wooyoung-23.09.04
    @Test
    @DisplayName("상품 id로 ProductOptionCategory 목록 출력")
    void successFindAllByProductId() {
        // given
        ProductCategory coffee = ProductCategory.builder()
            .id(1)
            .name("커피")
            .build();

        productCategoryRepository.save(coffee);

        Product espresso = Product.builder()
            .id(1)
            .productCategory(coffee)
            .name("에스프레소")
            .description("씁쓸한 에스프레소")
            .price(1500)
            .soldOutStatus(IN_STOCK)
            .picture("picture")
            .build();

        productRepository.save(espresso);

        OptionCategory size = OptionCategory.builder()
            .id(1)
            .name("사이즈")
            .build();

        OptionCategory iceAmount = OptionCategory.builder()
            .id(2)
            .name("얼음 양")
            .build();

        optionCategoryRepository.save(size);
        optionCategoryRepository.save(iceAmount);

        ProductOptionCategory espressoSize = ProductOptionCategory.builder()
            .product(espresso)
            .optionCategory(size)
            .build();

        ProductOptionCategory espressoIceAmount = ProductOptionCategory.builder()
            .product(espresso)
            .optionCategory(iceAmount)
            .build();

        System.out.println(espressoSize.toString());
        System.out.println(espressoIceAmount.toString());

        ProductOptionCategory espressoSizeDB =
            productOptionCategoryRepository.save(espressoSize);

        ProductOptionCategory espressoIceAmountDB =
            productOptionCategoryRepository.save(espressoIceAmount);

        // when
        List<ProductOptionCategory> espressoOptionCategory =
            productOptionCategoryRepository.findAllByProductId(espresso.getId());

        // then
        assertThat(espressoOptionCategory.get(0).getId()).isEqualTo(espressoSizeDB.getId());
        assertThat(espressoOptionCategory.get(0).getProduct()).isEqualTo(espressoSizeDB.getProduct());
        assertThat(espressoOptionCategory.get(0).getOptionCategory()).isEqualTo(espressoSizeDB.getOptionCategory());

        assertThat(espressoOptionCategory.get(1).getId()).isEqualTo(espressoIceAmountDB.getId());
        assertThat(espressoOptionCategory.get(1).getProduct()).isEqualTo(espressoIceAmountDB.getProduct());
        assertThat(espressoOptionCategory.get(1).getOptionCategory()).isEqualTo(espressoIceAmountDB.getOptionCategory());

    }

}