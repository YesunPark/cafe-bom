package com.zerobase.cafebom.productcategory.service;

import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import com.zerobase.cafebom.productcategory.repository.ProductCategoryRepository;
import com.zerobase.cafebom.productcategory.service.dto.ProductCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    // 관리자 상품 카테고리 등록-jiyeon-23.08.31
    @Override
    public void addProductCategory(ProductCategoryDto productCategoryDto) {

        productCategoryRepository.save(
                ProductCategory.builder()
                .name(productCategoryDto.getName())
                .build());
    }
}
