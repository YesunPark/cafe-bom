package com.zerobase.cafebom.admin.service;

import com.zerobase.cafebom.admin.service.AdminOptionCategoryService;
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.optioncategory.domain.OptionCategory;
import com.zerobase.cafebom.optioncategory.domain.OptionCategoryRepository;
import com.zerobase.cafebom.admin.dto.AdminOptionCategoryDto;
import com.zerobase.cafebom.admin.dto.AdminOptionCategoryForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zerobase.cafebom.exception.ErrorCode.NOT_FOUND_OPTION_CATEGORY;

@Service
@RequiredArgsConstructor
public class AdminOptionCategoryServiceImpl implements AdminOptionCategoryService {

    private final OptionCategoryRepository optionCategoryRepository;

    // 관리자 옵션 카테고리 수정-jiyeon-23.09.05
    @Transactional
    @Override
    public void modifyOptionCategory(Integer optionCategoryId, AdminOptionCategoryForm.Request optionCategoryForm) {
        OptionCategory optionCategory = optionCategoryRepository.findById(optionCategoryId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_OPTION_CATEGORY));

        AdminOptionCategoryDto.Request optionCategoryDto = AdminOptionCategoryDto.Request.from(optionCategoryForm);

        optionCategory.modifyOptionCategoryDto(optionCategoryDto.getName());

        optionCategoryRepository.save(optionCategory);
    }
}
