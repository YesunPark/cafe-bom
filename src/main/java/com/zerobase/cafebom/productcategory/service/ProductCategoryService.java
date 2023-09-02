package com.zerobase.cafebom.productcategory.service;

import com.zerobase.cafebom.productcategory.service.dto.ProductCategoryDto;
import org.springframework.stereotype.Service;

@Service
public interface ProductCategoryService {

    // 관리자 상품 카테고리 등록-jiyeon-23.08.31
    void addProductCategory(ProductCategoryDto productCategoryDto);
}
