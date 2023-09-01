package com.zerobase.cafebom.productcategory.service;

import com.zerobase.cafebom.productcategory.service.dto.ProductCategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductCategoryService {

    // 상품 카테고리 삭제-jiyeon-23.08.31
    void removeProductCategory(Integer id);

    // 상품 카테고리 전체 조회-jiyeon-23.08.31
    List<ProductCategoryDto.Response> findAllProductCategory();

    // 상품 카테고리Id 별 조회-jiyeon-23.08.31
    ProductCategoryDto.Response findByIdProductCategory(Integer id);
}
