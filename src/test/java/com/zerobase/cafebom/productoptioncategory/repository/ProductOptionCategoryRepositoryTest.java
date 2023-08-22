package com.zerobase.cafebom.productoptioncategory.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductOptionCategoryRepositoryTest {

    @Autowired
    private ProductOptionCategoryRepository productOptionCategoryRepository;
}