package com.zerobase.cafebom.admin.controller;

import com.zerobase.cafebom.admin.dto.AdminProductCategoryDto;
import com.zerobase.cafebom.admin.dto.AdminProductCategoryForm;
import com.zerobase.cafebom.admin.service.AdminProductCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "admin-product-category-controller", description = "관리자 상품 카테고리 CRUD API")
@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin/category")
public class AdminProductCategoryController {

    private final AdminProductCategoryService adminProductCategoryService;

    // jiyeon-23.09.18
    @ApiOperation(value = "상품 카테고리 등록", notes = "관리자가 상품 카테고리를 등록합니다.")
    @PostMapping
    public ResponseEntity<Void> productCategoryAdd(
            @Valid @RequestBody AdminProductCategoryForm.Request form) {
        adminProductCategoryService.addProductCategory(AdminProductCategoryDto.Request.from(form));
        return ResponseEntity.status(CREATED).build();
    }

    // jiyeon-23.09.18
    @ApiOperation(value = "상품 카테고리 수정", notes = "관리자가 상품 카테고리의 이름을 수정합니다.")
    @PutMapping("/{productCategoryId}")
    public ResponseEntity<Void> productCategoryModify(
            @PathVariable Integer productCategoryId,
            @Valid @RequestBody AdminProductCategoryForm.Request form) {
        adminProductCategoryService.modifyProductCategory(productCategoryId,
                AdminProductCategoryDto.Request.from(form));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.09.13
    @ApiOperation(value = "상품 카테고리 삭제", notes = "관리자가 상품 카테고리를 삭제합니다.")
    @DeleteMapping("/{productCategoryId}")
    public ResponseEntity<Void> productCategoryRemove(@PathVariable Integer productCategoryId) {
        adminProductCategoryService.removeProductCategory(productCategoryId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.09.13
    @ApiOperation(value = "상품 카테고리 전체조회", notes = "관리자가 상품 카테고리를 전체조회합니다.")
    @GetMapping
    public ResponseEntity<List<AdminProductCategoryForm.Response>> productCategoryList1() {
        List<AdminProductCategoryDto.Response> productCategoryList = adminProductCategoryService.findAllProductCategory();
        List<AdminProductCategoryForm.Response> productCategoryFormList = productCategoryList.stream()
                .map(AdminProductCategoryForm.Response::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(productCategoryFormList);
    }

    // jiyeon-23.09.13
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
