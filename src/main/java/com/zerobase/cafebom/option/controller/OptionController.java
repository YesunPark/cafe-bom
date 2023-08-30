package com.zerobase.cafebom.option.controller;

import com.zerobase.cafebom.option.controller.form.OptionForm;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.option.service.OptionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/option")
public class OptionController {

    private final OptionService optionService;
    private final OptionRepository optionRepository;

    @ApiOperation(value = "옵션 삭제(관리자)", notes = "관리자가 옵션을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> OptionRemove(@PathVariable Integer id) {
        optionService.removeOption(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "옵션 조회(관리자)", notes = "관리자가 옵션을 조회합니다.")
    @GetMapping
    public ResponseEntity<?> OptionList() {
        List<OptionForm.Response> optionList = optionService.findAllOption();
        return  ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(optionList);
    }

    @ApiOperation(value = "옵션Id별 조회(관리자)", notes = "관리자가 옵션Id를 통해 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> OptionListById(@PathVariable Integer id){
        OptionForm.Response response = optionService.findByIdOption(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }

}
