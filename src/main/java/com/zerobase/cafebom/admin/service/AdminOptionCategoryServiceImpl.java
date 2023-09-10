package com.zerobase.cafebom.admin.service;

import com.zerobase.cafebom.admin.dto.AdminOptionCategoryDto;
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.optioncategory.domain.OptionCategory;
import com.zerobase.cafebom.optioncategory.domain.OptionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.cafebom.exception.ErrorCode.OPTION_CATEGORY_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class AdminOptionCategoryServiceImpl implements AdminOptionCategoryService {

    private final OptionCategoryRepository optionCategoryRepository;

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
        AdminOptionCategoryDto.Response optionCategoryDto = AdminOptionCategoryDto.Response.from(optionCategory);
        return optionCategoryDto;
    }
}
