package com.zerobase.cafebom.optioncategory.controller;

import com.zerobase.cafebom.optioncategory.dto.OptionCategoryForm;
import com.zerobase.cafebom.optioncategory.service.OptionCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "option-controller", description = "관리자 옵션 카테고리 관리 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/option-category")
public class OptionCategoryController {

    private final OptionCategoryService optionCategoryService;

    @ApiOperation(value = "옵션 카테고리 등록(관리자)", notes = "관리자가 옵션 카테고리를 등록합니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> addOptionCategory(OptionCategoryForm.Request optionCategoryFormRequest) {
        optionCategoryService.addOptionCategory(optionCategoryFormRequest);
        return ResponseEntity.status(CREATED).build();
    }

}
