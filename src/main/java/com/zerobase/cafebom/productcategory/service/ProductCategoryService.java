package com.zerobase.cafebom.productcategory.service;

import com.zerobase.cafebom.productcategory.service.dto.ProductCategoryDto;
import org.springframework.stereotype.Service;

@Service
public interface ProductCategoryService {

    // 상품 카테고리 수정-jiyeon-23.08.31
    void modifyProductCategory(Integer id, ProductCategoryDto.Response productCategoryDto);
}
