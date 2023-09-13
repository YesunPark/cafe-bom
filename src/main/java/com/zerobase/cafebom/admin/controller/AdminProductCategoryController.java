package com.zerobase.cafebom.admin.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.zerobase.cafebom.admin.dto.AdminProductCategoryDto;
import com.zerobase.cafebom.admin.dto.AdminProductCategoryForm;
import com.zerobase.cafebom.admin.service.AdminProductCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "admin-product-category-controller", description = "관리자 상품 카테고리 CRUD API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class AdminProductCategoryController {

    private final AdminProductCategoryService adminProductCategoryService;

    @ApiOperation(value = "상품 카테고리 등록", notes = "관리자가 상품 카테고리를 등록합니다.")
    @PostMapping
    public ResponseEntity<Void> productCategoryAdd(
        @RequestBody AdminProductCategoryForm.Request form) {
        adminProductCategoryService.addProductCategory(AdminProductCategoryDto.Request.from(form));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @ApiOperation(value = "상품 카테고리 수정", notes = "관리자가 상품 카테고리의 이름을 수정합니다.")
    @PutMapping("/{productCategoryId}")
    public ResponseEntity<Void> productCategoryModify(
        @PathVariable Integer productCategoryId,
        @RequestBody AdminProductCategoryForm.Request form) {
        adminProductCategoryService.modifyProductCategory(productCategoryId,
            AdminProductCategoryDto.Request.from(form));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @ApiOperation(value = "상품 카테고리 삭제", notes = "관리자가 상품 카테고리를 삭제합니다.")
    @DeleteMapping("/{productCategoryId}")
    public ResponseEntity<Void> productCategoryRemove(@PathVariable Integer productCategoryId) {
        adminProductCategoryService.removeProductCategory(productCategoryId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @ApiOperation(value = "상품 카테고리 전체조회", notes = "관리자가 상품 카테고리를 전체조회합니다.")
    @GetMapping
    public ResponseEntity<List<AdminProductCategoryForm.Response>> productCategoryList1() {
        List<AdminProductCategoryDto.Response> productCategoryList = adminProductCategoryService.findAllProductCategory();
        List<AdminProductCategoryForm.Response> productCategoryFormList = productCategoryList.stream()
            .map(AdminProductCategoryForm.Response::from)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(productCategoryFormList);
    }

    @ApiOperation(value = "상품 카테고리Id별 조회", notes = "관리자가 상품 카테고리Id별 전체조회합니다.")
    @GetMapping("/{productCategoryId}")
    public ResponseEntity<AdminProductCategoryForm.Response> productCategoryById(
        @PathVariable Integer productCategoryId) {
        AdminProductCategoryDto.Response byIdProductCategoryDto = adminProductCategoryService.findByIdProductCategory(
            productCategoryId);
        AdminProductCategoryForm.Response byIdProductCategoryForm
            = AdminProductCategoryForm.Response.from(byIdProductCategoryDto);
        return ResponseEntity.ok().body(byIdProductCategoryForm);
    }
}
