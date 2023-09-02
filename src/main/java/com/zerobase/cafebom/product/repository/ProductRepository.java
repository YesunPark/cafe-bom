package com.zerobase.cafebom.product.repository;

import com.zerobase.cafebom.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findAllByProductCategoryId(Integer productCategoryId);

}