package com.zerobase.cafebom.option.controller;

import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.option.domain.type.OptionDto;
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
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/option")
public class OptionController {

    private final OptionService optionService;
    private final OptionRepository optionRepository;

    @ApiOperation(value = "옵션 삭제(관리자)", notes = "관리자가 옵션을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> OptionRemove(@PathVariable Integer id) {
        boolean result = optionService.removeOption(id);
        if (result) {
            return new ResponseEntity<>(OK);
        } else {
            return new ResponseEntity<>(ErrorCode.OPTION_REMOVE_FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "옵션 조회(관리자)", notes = "관리자가 옵션을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<OptionDto>> OptionList() {
        List<OptionDto> optionList = optionService.findAllOption();
        return  ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(optionList);
    }

    @ApiOperation(value = "옵션Id별 조회(관리자)", notes = "관리자가 옵션Id를 통해 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> OptionListById(@PathVariable Integer id){
        Optional<Option> optionById = optionRepository.findById(id);
        OptionDto optionDto = optionService.findByIdOption(id);
        if (optionById.isPresent()) {
            Option option = optionById.get();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(optionDto);
        } else {
            return new ResponseEntity<>(ErrorCode.OPTION_NOT_EXIST, HttpStatus.BAD_REQUEST);
        }
    }



}
