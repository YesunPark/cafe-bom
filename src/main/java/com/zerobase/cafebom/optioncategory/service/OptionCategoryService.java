package com.zerobase.cafebom.optioncategory.service;

import com.zerobase.cafebom.optioncategory.dto.OptionCategoryForm;
import org.springframework.stereotype.Service;

@Service
public interface OptionCategoryService {

    // 관리자 옵션 카테고리 수정-jiyeon-23.09.05
    void modifyOptionCategory(Integer optionCategoryId, OptionCategoryForm.Request optionCategoryForm);

}
