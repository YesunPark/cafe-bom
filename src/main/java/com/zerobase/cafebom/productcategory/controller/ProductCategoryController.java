package com.zerobase.cafebom.productcategory.controller;

import com.zerobase.cafebom.productcategory.dto.ProductCategoryDto;
import com.zerobase.cafebom.productcategory.dto.ProductCategoryForm;
import com.zerobase.cafebom.productcategory.service.ProductCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "product-category-controller", description = "관리자 상품 카테고리 관리 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
//@PreAuthorize(("hasRole('ADMIN')"))
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @ApiOperation(value = "상품 카테고리 등록(관리자)", notes = "관리자가 상품 카테고리를 등록합니다.")
    @PostMapping
    public ResponseEntity<?> productCategoryAdd(@RequestBody ProductCategoryForm.Request form) {
        productCategoryService.addProductCategory(ProductCategoryDto.Request.from(form));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @ApiOperation(value = "상품 카테고리 수정(관리자)", notes = "관리자가 상품 카테고리를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<?> productCategoryModify(
        @PathVariable Integer id,
        @RequestBody ProductCategoryForm.Request form) {
        productCategoryService.modifyProductCategory(id, ProductCategoryDto.Request.from(form));
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
