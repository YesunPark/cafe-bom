package com.zerobase.cafebom.product.repository;

import com.zerobase.cafebom.product.domain.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByProductCategoryId(Integer productCategoryId);
}