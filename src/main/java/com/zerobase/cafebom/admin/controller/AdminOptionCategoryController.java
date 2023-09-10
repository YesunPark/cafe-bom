package com.zerobase.cafebom.admin.controller;

import com.zerobase.cafebom.admin.dto.AdminOptionCategoryForm;
import com.zerobase.cafebom.auth.service.AdminOptionCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "admin-option-controller", description = "관리자 옵션 카테고리 CRUD API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/option-category")
public class AdminOptionCategoryController {

    private final AdminOptionCategoryService adminOptionCategoryService;

    @ApiOperation(value = "옵션 카테고리 등록", notes = "관리자가 옵션 카테고리를 등록합니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> OptionCategoryAdd(AdminOptionCategoryForm.Request optionCategoryFormRequest) {
        adminOptionCategoryService.addOptionCategory(optionCategoryFormRequest);
        return ResponseEntity.status(CREATED).build();
    }
}
