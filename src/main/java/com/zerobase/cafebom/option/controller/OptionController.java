package com.zerobase.cafebom.option.controller;

import com.zerobase.cafebom.option.controller.form.OptionModifyForm;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.option.service.OptionService;
import com.zerobase.cafebom.option.service.dto.OptionModifyDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/option")
public class OptionController {

    private final OptionService optionService;
    private final OptionRepository optionRepository;

    @ApiOperation(value = "옵션 수정(관리자)", notes = "관리자가 옵션을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<?> OptionModify(
            @PathVariable Integer id,
            @RequestBody OptionModifyForm form) {
        optionService.modifyOption(id, OptionModifyDto.Request.from(form));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
