package com.zerobase.cafebom.productcategory.service;

import static com.zerobase.cafebom.exception.ErrorCode.PRODUCTCATEGORY_NOT_EXISTS;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.productcategory.domain.ProductCategory;
import com.zerobase.cafebom.productcategory.domain.ProductCategoryRepository;
import com.zerobase.cafebom.productcategory.dto.ProductCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    // 관리자 상품 카테고리 등록-jiyeon-23.08.31
    @Override
    public void addProductCategory(ProductCategoryDto.Request productCategoryDto) {
        productCategoryRepository.save(
            ProductCategory.builder()
                .name(productCategoryDto.getName())
                .build());
    }

    // 상품 카테고리 수정-jiyeon-23.08.31
    @Transactional
    @Override
    public void modifyProductCategory(Integer id, ProductCategoryDto.Request productCategoryDto) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
            .orElseThrow(() -> new CustomException(PRODUCTCATEGORY_NOT_EXISTS));
        productCategory.modifyProductCategory(productCategoryDto.getName());
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import com.zerobase.cafebom.productcategory.repository.ProductCategoryRepository;
import com.zerobase.cafebom.productcategory.service.dto.ProductCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    // 상품 카테고리 전체 조회-jiyeon-23.08.31
    @Override
    public List<ProductCategoryDto.Response> findAllProductCategory() {
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        List<ProductCategoryDto.Response> productCategoryDtoList = productCategoryList.stream()
                .map(ProductCategoryDto.Response::from)
                .collect(Collectors.toList());
        return productCategoryDtoList;
    }

    // 상품 카테고리Id 별 조회-jiyeon-23.08.31
    @Override
    public ProductCategoryDto.Response findByIdProductCategory(Integer id) {
        ProductCategory productCategoryId = productCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT_CATEGORY));
        ProductCategoryDto.Response dtoResponse = ProductCategoryDto.Response.from(productCategoryId);
        return dtoResponse;
    }
}
