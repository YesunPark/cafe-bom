package com.zerobase.cafebom.admin.controller;

import com.zerobase.cafebom.optioncategory.dto.OptionCategoryForm;
import com.zerobase.cafebom.optioncategory.service.OptionCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "option-category-controller", description = "관리자 옵션 카테고리 CRUD API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/option-category")
public class AdminOptionCategoryController {

    private final OptionCategoryService optionCategoryService;

    // jiyeon-23.09.05
    @ApiOperation(value = "옵션 카테고리 수정", notes = "관리자가 옵션 카테고리를 수정합니다.")
    @PatchMapping("/{optionCategoryId}")
    public ResponseEntity<Void> optionCategoryModify(
            @PathVariable Integer optionCategoryId,
            @RequestBody OptionCategoryForm.Request optionCategoryForm) {
        optionCategoryService.modifyOptionCategory(optionCategoryId, optionCategoryForm);
        return null;
    }
}
