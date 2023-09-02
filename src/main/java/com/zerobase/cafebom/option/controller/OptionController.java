package com.zerobase.cafebom.option.controller;

import com.zerobase.cafebom.option.controller.form.OptionAddForm;
import com.zerobase.cafebom.option.controller.form.OptionModifyForm;
import com.zerobase.cafebom.option.service.OptionService;
import com.zerobase.cafebom.option.service.dto.OptionAddDto;
import com.zerobase.cafebom.option.service.dto.OptionModifyDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "product-controller", description = "관리자 옵션 CRUD API")
@Controller
@RequestMapping("/admin/option")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class OptionController {

    private final OptionService optionService;

    // jiyeon-23.08.30
    @ApiOperation(value = "옵션 등록(관리자)", notes = "관리자가 옵션을 등록합니다.")
    @PostMapping
    public ResponseEntity<?> optionAdd(@RequestBody OptionAddForm optionAddForm) {
        optionService.addOption(OptionAddDto.from(optionAddForm));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // jiyeon-23.08.30
    @ApiOperation(value = "옵션 수정(관리자)", notes = "관리자가 옵션을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<?> optionModify(
            @PathVariable Integer id,
            @RequestBody OptionModifyForm form) {
        optionService.modifyOption(id, OptionModifyDto.Request.from(form));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
