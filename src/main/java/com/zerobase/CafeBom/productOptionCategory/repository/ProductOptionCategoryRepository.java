package com.zerobase.CafeBom.productOptionCategory.repository;

import com.zerobase.CafeBom.productOptionCategory.domain.entity.ProductOptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionCategoryRepository extends JpaRepository<ProductOptionCategory, Integer> {

}
