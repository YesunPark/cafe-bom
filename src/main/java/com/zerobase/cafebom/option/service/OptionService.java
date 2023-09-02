package com.zerobase.cafebom.option.service;

import com.zerobase.cafebom.option.service.dto.OptionAddDto;
import org.springframework.stereotype.Service;

@Service
public interface OptionService {
    // 옵션 등록-jiyeon-23.08.30
    void addOption(OptionAddDto optionAddDto);
}
