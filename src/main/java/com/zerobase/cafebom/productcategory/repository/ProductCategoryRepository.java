package com.zerobase.cafebom.productcategory.repository;

import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    void deleteAll();

}
