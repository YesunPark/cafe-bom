package com.zerobase.cafebom.admin.service;

import com.zerobase.cafebom.admin.dto.AdminOptionCategoryDto;
import com.zerobase.cafebom.admin.dto.AdminOptionCategoryForm;
import com.zerobase.cafebom.optioncategory.domain.OptionCategory;
import com.zerobase.cafebom.optioncategory.domain.OptionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOptionCategoryServiceImpl implements AdminOptionCategoryService {

    private final OptionCategoryRepository optionCategoryRepository;

    // 관리자 옵션 카테고리 등록-jiyeon-23.09.05
    @Override
    public void addOptionCategory(AdminOptionCategoryForm.Request optionCategoryFormRequest) {
        AdminOptionCategoryDto.Request optionCategoryDto = AdminOptionCategoryDto.Request.from(optionCategoryFormRequest);

        optionCategoryRepository.save(OptionCategory.builder()
                .name(optionCategoryDto.getName())
                .build());
    }
}
