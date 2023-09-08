package com.zerobase.cafebom.admin.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.zerobase.cafebom.productcategory.dto.ProductCategoryDto;
import com.zerobase.cafebom.productcategory.dto.ProductCategoryForm;
import com.zerobase.cafebom.productcategory.service.ProductCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "admin-product-category-controller", description = "관리자 상품 카테고리 CRUD API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
@PreAuthorize(("hasRole('ADMIN')"))
public class AdminProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @ApiOperation(value = "상품 카테고리 등록", notes = "관리자가 상품 카테고리를 등록합니다.")
    @PostMapping
    public ResponseEntity<?> productCategoryAdd(@RequestBody ProductCategoryForm.Request form) {
        productCategoryService.addProductCategory(ProductCategoryDto.Request.from(form));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @ApiOperation(value = "상품 카테고리 수정", notes = "관리자가 상품 카테고리의 이름을 수정합니다.")
    @PutMapping("/{productCategoryId}")
    public ResponseEntity<?> productCategoryModify(
        @PathVariable Integer productCategoryId,
        @RequestBody ProductCategoryForm.Request form) {
        productCategoryService.modifyProductCategory(productCategoryId,
            ProductCategoryDto.Request.from(form));
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
