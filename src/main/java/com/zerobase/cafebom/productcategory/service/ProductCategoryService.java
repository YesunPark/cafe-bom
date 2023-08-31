package com.zerobase.cafebom.productcategory.service;

import org.springframework.stereotype.Service;

@Service
public interface ProductCategoryService {

    // 상품 카테고리 삭제-jiyeon-23.08.31
    void removeProductCategory(Integer id);
}
