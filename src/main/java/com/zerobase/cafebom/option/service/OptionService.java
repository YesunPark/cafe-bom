package com.zerobase.cafebom.option.service;

import com.zerobase.cafebom.option.domain.type.OptionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OptionService {

    // 옵션 삭제-jiyeon-23.08.30
    boolean removeOption(Integer id);

    // 옵션 전체 조회-jiyeon-23.08.30
    List<OptionDto> findAllOption();

    // 옵션Id별 조회-jiyeon-23.08.30
    OptionDto findByIdOption(Integer id);
}
