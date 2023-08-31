package com.zerobase.cafebom.productcategory.service;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import com.zerobase.cafebom.productcategory.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zerobase.cafebom.exception.ErrorCode.NOT_FOUND_PRODUCT_CATEGORY;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService{

    private final ProductCategoryRepository productCategoryRepository;

    // 상품 카테고리 삭제-jiyeon-23.08.31
    @Override
    public void removeProductCategory(Integer id) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT_CATEGORY));
        productCategoryRepository.deleteById(productCategory.getId());
    }
}
