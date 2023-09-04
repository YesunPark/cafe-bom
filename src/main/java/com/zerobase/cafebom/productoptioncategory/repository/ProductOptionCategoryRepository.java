package com.zerobase.cafebom.productoptioncategory.repository;

import com.zerobase.cafebom.productoptioncategory.domain.entity.ProductOptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionCategoryRepository extends
    JpaRepository<ProductOptionCategory, Integer> {

}
