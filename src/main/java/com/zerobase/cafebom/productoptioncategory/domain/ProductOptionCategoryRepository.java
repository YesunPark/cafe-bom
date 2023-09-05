package com.zerobase.cafebom.productoptioncategory.domain;

import com.zerobase.cafebom.productoptioncategory.domain.ProductOptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionCategoryRepository extends
    JpaRepository<ProductOptionCategory, Integer> {

}
