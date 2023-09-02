package com.zerobase.cafebom.option.service;

import com.zerobase.cafebom.option.service.dto.OptionModifyDto;
import com.zerobase.cafebom.option.service.dto.OptionAddDto;
import org.springframework.stereotype.Service;

@Service
public interface OptionService {
    // 옵션 등록-jiyeon-23.08.30
    void addOption(OptionAddDto optionAddDto);

    // 옵션 수정-jiyeon-23.08.30
    void modifyOption(Integer id, OptionModifyDto.Request request);


@Service
public interface OptionService {

    // 옵션 삭제-jiyeon-23.08.30
    void removeOption(Integer id);

    // 옵션 전체 조회-jiyeon-23.08.30
    List<OptionForm.Response> findAllOption();

    // 옵션Id별 조회-jiyeon-23.08.30
    OptionForm.Response findByIdOption(Integer id);
}
