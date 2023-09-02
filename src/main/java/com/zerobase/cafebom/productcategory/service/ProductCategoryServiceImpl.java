package com.zerobase.cafebom.productcategory.service;

import com.zerobase.cafebom.exception.CustomException;
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
import org.springframework.transaction.annotation.Transactional;

import static com.zerobase.cafebom.exception.ErrorCode.NOT_FOUND_PRODUCT_CATEGORY;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService{

    private final ProductCategoryRepository productCategoryRepository;

    // 상품 카테고리 수정-jiyeon-23.08.31
    @Transactional
    @Override
    public void modifyProductCategory(Integer id, ProductCategoryDto.Request productCategoryDto) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT_CATEGORY));
        productCategory.modifyProductCategory(productCategoryDto.getName());
    }
}
