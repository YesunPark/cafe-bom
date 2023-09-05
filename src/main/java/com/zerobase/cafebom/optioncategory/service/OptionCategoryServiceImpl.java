package com.zerobase.cafebom.optioncategory.service;

import com.zerobase.cafebom.optioncategory.domain.OptionCategory;
import com.zerobase.cafebom.optioncategory.domain.OptionCategoryRepository;
import com.zerobase.cafebom.optioncategory.dto.OptionCategoryDto;
import com.zerobase.cafebom.optioncategory.dto.OptionCategoryForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionCategoryServiceImpl implements OptionCategoryService {

    private final OptionCategoryRepository optionCategoryRepository;

    // 관리자 옵션 카테고리 등록 -jiyeon-23.09.05
    @Override
    public void addOptionCategory(OptionCategoryForm.Request optionCategoryFormRequest) {
        OptionCategoryDto.Request optionCategoryDto = OptionCategoryDto.Request.from(optionCategoryFormRequest);

        optionCategoryRepository.save(OptionCategory.builder()
                .name(optionCategoryDto.getName())
                .build());
    }

}
