package com.zerobase.CafeBom.productCategory.repository;

import com.zerobase.CafeBom.productCategory.domain.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

}
