package com.zerobase.cafebom.option.service;

import com.zerobase.cafebom.option.dto.OptionDto.Request;
import com.zerobase.cafebom.option.dto.OptionForm;
import com.zerobase.cafebom.option.dto.OptionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OptionService {

    // 옵션 등록-jiyeon-23.08.30
    void addOption(OptionDto.Request optionAddDto);

    // 옵션 수정-jiyeon-23.08.30
    void modifyOption(Integer id, OptionDto.Request request);

    // 옵션 삭제-jiyeon-23.08.30
    void removeOption(Integer id);

    // 옵션 전체 조회-jiyeon-23.08.30
    List<OptionForm.Response> findAllOption();

    // 옵션Id별 조회-jiyeon-23.08.30
    OptionForm.Response findByIdOption(Integer id);
}
