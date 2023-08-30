package com.zerobase.cafebom.option.controller;

import com.zerobase.cafebom.option.controller.form.OptionModifyForm;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.option.service.OptionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.zerobase.cafebom.exception.ErrorCode.OPTION_MODIFY_FAIL;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

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
        boolean result = optionService.modifyOption(id, form);
        if (result) {
            return new ResponseEntity<>(OK);
        } else {
            return new ResponseEntity<>(OPTION_MODIFY_FAIL, BAD_REQUEST);
        }
    }
}
