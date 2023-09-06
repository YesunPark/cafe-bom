package com.zerobase.cafebom.optioncategory.service;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.optioncategory.domain.OptionCategory;
import com.zerobase.cafebom.optioncategory.domain.OptionCategoryRepository;
import com.zerobase.cafebom.optioncategory.dto.OptionCategoryDto;
import com.zerobase.cafebom.optioncategory.dto.OptionCategoryForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zerobase.cafebom.exception.ErrorCode.NOT_FOUND_OPTION_CATEGORY;

@Service
@RequiredArgsConstructor
public class OptionCategoryServiceImpl implements OptionCategoryService{

    private final OptionCategoryRepository optionCategoryRepository;

    // 관리자 옵션 카테고리 수정-jiyeon-23.09.05
    @Transactional
    @Override
    public void modifyOptionCategory(Integer optionCategoryId, OptionCategoryForm.Request optionCategoryForm) {
        OptionCategory optionCategory = optionCategoryRepository.findById(optionCategoryId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_OPTION_CATEGORY));

        OptionCategoryDto.Request optionCategoryDto = OptionCategoryDto.Request.from(optionCategoryForm);

        optionCategory.modifyOptionCategoryDto(optionCategoryDto.getName());

        optionCategoryRepository.save(optionCategory);
    }
}
