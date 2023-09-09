package com.zerobase.cafebom.admin.controller;

import com.zerobase.cafebom.admin.dto.AdminProductCategoryDto;
import com.zerobase.cafebom.admin.dto.AdminProductCategoryForm;
import com.zerobase.cafebom.admin.service.AdminProductCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "admin-product-category-controller", description = "관리자 상품 카테고리 CRUD API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
//@PreAuthorize(("hasRole('ADMIN')"))
public class AdminProductCategoryController {

    private final AdminProductCategoryService adminProductCategoryService;

    @ApiOperation(value = "상품 카테고리 등록", notes = "관리자가 상품 카테고리를 등록합니다.")
    @PostMapping
    public ResponseEntity<?> productCategoryAdd(@RequestBody AdminProductCategoryForm.Request form) {
        adminProductCategoryService.addProductCategory(AdminProductCategoryDto.Request.from(form));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @ApiOperation(value = "상품 카테고리 수정", notes = "관리자가 상품 카테고리의 이름을 수정합니다.")
    @PutMapping("/{productCategoryId}")
    public ResponseEntity<?> productCategoryModify(
            @PathVariable Integer productCategoryId,
            @RequestBody AdminProductCategoryForm.Request form) {
        adminProductCategoryService.modifyProductCategory(productCategoryId,
                AdminProductCategoryDto.Request.from(form));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @ApiOperation(value = "상품 카테고리 삭제", notes = "관리자가 상품 카테고리를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> productCategoryRemove(@PathVariable Integer id) {
        adminProductCategoryService.removeProductCategory(id);
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
    @GetMapping("/{id}")
    public ResponseEntity<AdminProductCategoryForm.Response> productCategoryById(@PathVariable Integer id) {
        AdminProductCategoryDto.Response byIdProductCategoryDto = adminProductCategoryService.findByIdProductCategory(id);
        AdminProductCategoryForm.Response byIdProductCategoryForm
                = AdminProductCategoryForm.Response.from(byIdProductCategoryDto);
        return ResponseEntity.ok().body(byIdProductCategoryForm);
    }
}
