package com.zerobase.cafebom.option.controller;

import com.zerobase.cafebom.option.dto.OptionForm;
import com.zerobase.cafebom.option.service.OptionService;
import com.zerobase.cafebom.option.dto.OptionDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "option-controller", description = "관리자 옵션 CRUD API")
@Controller
@RequestMapping("/admin/option")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class OptionController {

    private final OptionService optionService;

    // jiyeon-23.08.30
    @ApiOperation(value = "옵션 등록(관리자)", notes = "관리자가 옵션을 등록합니다.")
    @PostMapping
    public ResponseEntity<?> optionAdd(@RequestBody OptionForm.Request optionFormRequest) {
        optionService.addOption(OptionDto.Request.from(optionFormRequest));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // jiyeon-23.08.30
    @ApiOperation(value = "옵션 수정(관리자)", notes = "관리자가 옵션을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<?> optionModify(
        @PathVariable Integer id,
        @RequestBody OptionForm.Request form) {
        optionService.modifyOption(id, OptionDto.Request.from(form));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // jiyeon-23.08.30
    @ApiOperation(value = "옵션 삭제(관리자)", notes = "관리자가 옵션을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> optionRemove(@PathVariable Integer id) {
        optionService.removeOption(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // jiyeon-23.08.30
    @ApiOperation(value = "옵션 전체 조회(관리자)", notes = "관리자가 옵션을 전체 조회합니다.")
    @GetMapping
    public ResponseEntity<?> optionList() {
        List<OptionForm.Response> optionList = optionService.findAllOption();
        return ResponseEntity.ok().body(optionList);
    }

    // jiyeon-23.08.30
    @ApiOperation(value = "옵션Id별 조회(관리자)", notes = "관리자가 옵션Id를 통해 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> optionListById(@PathVariable Integer id) {
        OptionForm.Response response = optionService.findByIdOption(id);
        return ResponseEntity.ok().body(response);
    }
}
