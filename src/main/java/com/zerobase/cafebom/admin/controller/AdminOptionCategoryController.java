package com.zerobase.cafebom.admin.controller;

import com.zerobase.cafebom.admin.dto.AdminOptionCategoryForm;
import com.zerobase.cafebom.admin.service.AdminOptionCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "admin-option-category-controller", description = "관리자 옵션 카테고리 CRUD API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/option-category")
public class AdminOptionCategoryController {

    private final AdminOptionCategoryService adminOptionCategoryService;

    // jiyeon-23.09.05
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "옵션 카테고리 수정", notes = "관리자가 옵션 카테고리를 수정합니다.")
    @PatchMapping("/{optionCategoryId}")
    public ResponseEntity<Void> optionCategoryModify(
            @PathVariable Integer optionCategoryId,
            @RequestBody AdminOptionCategoryForm.Request optionCategoryForm) {
        adminOptionCategoryService.modifyOptionCategory(optionCategoryId, optionCategoryForm);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
