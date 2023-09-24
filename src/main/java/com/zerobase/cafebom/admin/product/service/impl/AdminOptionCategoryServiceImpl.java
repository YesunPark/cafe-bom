package com.zerobase.cafebom.admin.product.service.impl;

import static com.zerobase.cafebom.common.exception.ErrorCode.OPTION_CATEGORY_NOT_EXISTS;

import com.zerobase.cafebom.admin.product.AdminOptionCategoryService;
import com.zerobase.cafebom.admin.product.dto.AdminOptionCategoryDto;
import com.zerobase.cafebom.admin.product.dto.AdminOptionCategoryForm;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.product.domain.OptionCategory;
import com.zerobase.cafebom.front.product.domain.OptionCategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminOptionCategoryServiceImpl implements AdminOptionCategoryService {

    private final OptionCategoryRepository optionCategoryRepository;

    // 관리자 옵션 카테고리 등록-jiyeon-23.09.05
    @Override
    public void addOptionCategory(AdminOptionCategoryForm.Request optionCategoryFormRequest) {
        AdminOptionCategoryDto.Request optionCategoryDto = AdminOptionCategoryDto.Request.from(
            optionCategoryFormRequest);

        optionCategoryRepository.save(OptionCategory.builder()
            .name(optionCategoryDto.getName())
            .build());
    }

    // 관리자 옵션 카테고리 수정-jiyeon-23.09.05
    @Transactional
    @Override
    public void modifyOptionCategory(Integer optionCategoryId,
        AdminOptionCategoryForm.Request optionCategoryForm) {
        OptionCategory optionCategory = optionCategoryRepository.findById(optionCategoryId)
            .orElseThrow(() -> new CustomException(OPTION_CATEGORY_NOT_EXISTS));

        AdminOptionCategoryDto.Request optionCategoryDto = AdminOptionCategoryDto.Request.from(
            optionCategoryForm);

        optionCategory.modifyOptionCategoryDto(optionCategoryDto.getName());

        optionCategoryRepository.save(optionCategory);
    }

    // jiyeon-23.09.09
    @Override
    public void removeOptionCategory(Integer optionCategoryId) {
        OptionCategory optionCategory = optionCategoryRepository.findById(optionCategoryId)
            .orElseThrow(() -> new CustomException(OPTION_CATEGORY_NOT_EXISTS));
        optionCategoryRepository.deleteById(optionCategoryId);
    }

    // jiyeon-23.09.09
    @Override
    public List<AdminOptionCategoryDto.Response> findOptionCategoryList() {
        List<OptionCategory> optionCategoryList = optionCategoryRepository.findAll();
        List<AdminOptionCategoryDto.Response> optionCategoryDtoList =
            optionCategoryList.stream()
                .map(AdminOptionCategoryDto.Response::from)
                .collect(Collectors.toList());
        return optionCategoryDtoList;
    }

    // jiyeon-23.09.09
    @Override
    public AdminOptionCategoryDto.Response findOptionCategoryListById(Integer optionCategoryId) {
        OptionCategory optionCategory = optionCategoryRepository.findById(optionCategoryId)
            .orElseThrow(() -> new CustomException(OPTION_CATEGORY_NOT_EXISTS));
        AdminOptionCategoryDto.Response optionCategoryDto = AdminOptionCategoryDto.Response.from(
            optionCategory);
        return optionCategoryDto;
    }
}
