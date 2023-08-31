package com.zerobase.cafebom.productcategory.controller;

import com.zerobase.cafebom.productcategory.service.ProductCategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product-category")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @ApiOperation(value = "상품 카테고리 삭제(관리자)", notes = "관리자가 상품 카테고리를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> productCategoryRemove(@PathVariable Integer id) {
        productCategoryService.removeProductCategory(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }


}
