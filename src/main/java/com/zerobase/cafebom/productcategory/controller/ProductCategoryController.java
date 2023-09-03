package com.zerobase.cafebom.productcategory.controller;

import com.zerobase.cafebom.productcategory.controller.form.ProductCategoryForm;
import com.zerobase.cafebom.productcategory.service.ProductCategoryService;
import com.zerobase.cafebom.productcategory.service.dto.ProductCategoryDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "product-category-controller", description = "관리자 카테고리 CRUD API")
@Controller
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/category")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @ApiOperation(value = "상품 카테고리 삭제(관리자)", notes = "관리자가 상품 카테고리를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> productCategoryRemove(@PathVariable Integer id) {
        productCategoryService.removeProductCategory(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @ApiOperation(value = "상품 카테고리 전체조회(관리자)", notes = "관리자가 상품 카테고리를 전체조회합니다.")
    @GetMapping
    public ResponseEntity<?> productCategoryList() {
        List<ProductCategoryDto.Response> productCategoryList = productCategoryService.findAllProductCategory();
        List<ProductCategoryForm.Response> productCategoryFormList = productCategoryList.stream()
                .map(ProductCategoryForm.Response::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(productCategoryFormList);
    }

    @ApiOperation(value = "상품 카테고리Id별 조회(관리자)", notes = "관리자가 상품 카테고리Id별 전체조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> productCategoryById(@PathVariable Integer id) {
        ProductCategoryDto.Response byIdProductCategoryDto = productCategoryService.findByIdProductCategory(id);
        ProductCategoryForm.Response byIdProductCategoryForm
                = ProductCategoryForm.Response.from(byIdProductCategoryDto);
        return ResponseEntity.ok().body(byIdProductCategoryDto);

    }
}
