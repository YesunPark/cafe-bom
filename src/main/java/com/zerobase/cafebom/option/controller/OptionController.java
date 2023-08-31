package com.zerobase.cafebom.option.controller;

import com.zerobase.cafebom.option.controller.form.OptionAddForm;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.option.service.OptionService;
import com.zerobase.cafebom.option.service.dto.OptionAddDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "product-controller", description = "관리자 옵션 CRUD API")
@Controller
@RequestMapping("/admin/option")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class OptionController {

    private final OptionRepository optionRepository;
    private final OptionService optionService;

    @ApiOperation(value = "옵션 등록(관리자)", notes = "관리자가 옵션을 등록합니다.")
    @PostMapping
    public ResponseEntity<?> optionAdd(@RequestBody OptionAddForm optionAddForm) {
        optionService.addOption(OptionAddDto.from(optionAddForm));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
