package com.zerobase.cafebom.productcategory.controller;

import com.zerobase.cafebom.productcategory.controller.form.ProductCategoryForm;
import com.zerobase.cafebom.productcategory.service.ProductCategoryService;
import com.zerobase.cafebom.productcategory.service.dto.ProductCategoryDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product-category")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @ApiOperation(value = "상품 카테고리 수정(관리자)", notes = "관리자가 상품 카테고리를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<?> productCategoryModify(
            @PathVariable Integer id,
            @RequestBody ProductCategoryForm.Response form) {
        productCategoryService.modifyProductCategory(id, ProductCategoryDto.Response.from(form));
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
