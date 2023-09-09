package com.zerobase.cafebom.admin.service;

import com.zerobase.cafebom.admin.dto.AdminProductCategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminProductCategoryService {

    // 관리자 상품 카테고리 등록-jiyeon-23.08.31
    void addProductCategory(AdminProductCategoryDto.Request productCategoryDto);

    // 상품 카테고리 수정-jiyeon-23.08.31
    void modifyProductCategory(Integer productCategoryId, AdminProductCategoryDto.Request productCategoryDto);

    // 상품 카테고리 삭제-jiyeon-23.08.31
    void removeProductCategory(Integer productCategoryId);

    // 상품 카테고리 전체 조회-jiyeon-23.08.31
    List<AdminProductCategoryDto.Response> findAllProductCategory();

    // 상품 카테고리Id 별 조회-jiyeon-23.08.31
    AdminProductCategoryDto.Response findByIdProductCategory(Integer productCategoryId);
}
