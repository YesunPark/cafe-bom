package com.zerobase.cafebom.productOptionCategory.repository;

import com.zerobase.cafebom.productOptionCategory.domain.entity.ProductOptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionCategoryRepository extends JpaRepository<ProductOptionCategory, Integer> {

}
