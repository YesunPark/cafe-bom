package com.zerobase.cafebom.productcategory.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.zerobase.cafebom.productcategory.controller.form.ProductCategoryForm;
import com.zerobase.cafebom.productcategory.service.ProductCategoryService;
import com.zerobase.cafebom.productcategory.service.dto.ProductCategoryDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
