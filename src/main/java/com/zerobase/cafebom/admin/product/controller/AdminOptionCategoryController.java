package com.zerobase.cafebom.admin.product.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.zerobase.cafebom.admin.product.AdminOptionCategoryService;
import com.zerobase.cafebom.admin.product.dto.AdminOptionCategoryDto;
import com.zerobase.cafebom.admin.product.dto.AdminOptionCategoryForm;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "admin-option-category-controller", description = "관리자 옵션 카테고리 CRUD API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/option-category")
public class AdminOptionCategoryController {

    private final AdminOptionCategoryService adminOptionCategoryService;

    // jiyeon-23.09.05
    @ApiOperation(value = "옵션 카테고리 등록", notes = "관리자가 옵션 카테고리를 등록합니다.")
    @PostMapping
    public ResponseEntity<Void> OptionCategoryAdd(
            @RequestBody AdminOptionCategoryForm.Request optionCategoryFormRequest) {
        adminOptionCategoryService.addOptionCategory(optionCategoryFormRequest);
        return ResponseEntity.status(CREATED).build();
    }

    // jiyeon-23.09.05
    @ApiOperation(value = "옵션 카테고리 수정", notes = "관리자가 옵션 카테고리를 수정합니다.")
    @PatchMapping("/{optionCategoryId}")
    public ResponseEntity<Void> optionCategoryModify(
            @PathVariable Integer optionCategoryId,
            @RequestBody AdminOptionCategoryForm.Request optionCategoryForm) {
        adminOptionCategoryService.modifyOptionCategory(optionCategoryId, optionCategoryForm);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.09.09
    @ApiOperation(value = "옵션 카테고리 삭제", notes = "관리자가 옵션 카테고리를 삭제합니다.")
    @DeleteMapping("/{optionCategoryId}")
    public ResponseEntity<Void> OptionCategoryRemove(@PathVariable Integer optionCategoryId) {
        adminOptionCategoryService.removeOptionCategory(optionCategoryId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.09.09
    @ApiOperation(value = "옵션 카테고리 전체 목록 조회", notes = "관리자가 옵션 카테고리 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<AdminOptionCategoryForm.Response>> OptionCategoryList() {
        List<AdminOptionCategoryDto.Response> optionCategoryDtoList =
                adminOptionCategoryService.findOptionCategoryList();
        List<AdminOptionCategoryForm.Response> adminOptionCategoryFormList =
                optionCategoryDtoList.stream()
                        .map(AdminOptionCategoryForm.Response::from)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(adminOptionCategoryFormList);
    }

    // jiyeon-23.09.09
    @ApiOperation(value = "옵션 카테고리 단건 조회", notes = "관리자가 하나의 옵션 카테고리에 대한 정보를 조회합니다.")
    @GetMapping("/{optionCategoryId}")
    public ResponseEntity<AdminOptionCategoryForm.Response> optionCategoryListById(
            @PathVariable Integer optionCategoryId) {
        AdminOptionCategoryDto.Response optionCategoryDto =
                adminOptionCategoryService.findOptionCategoryListById(optionCategoryId);
        AdminOptionCategoryForm.Response optionCategoryForm =
                AdminOptionCategoryForm.Response.from(optionCategoryDto);
        return ResponseEntity.ok(optionCategoryForm);
    }
}