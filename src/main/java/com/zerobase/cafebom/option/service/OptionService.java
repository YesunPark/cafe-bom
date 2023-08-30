package com.zerobase.cafebom.option.service;

import com.zerobase.cafebom.option.controller.form.OptionModifyForm;
import org.springframework.stereotype.Service;

@Service
public interface OptionService {

    // 옵션 수정-jiyeon-23.08.30
    boolean modifyOption(Integer id, OptionModifyForm form);
}
