package com.zerobase.cafebom.option.service;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.option.service.dto.OptionAddDto;
import com.zerobase.cafebom.optioncategory.domain.entity.OptionCategory;
import com.zerobase.cafebom.optioncategory.repository.OptionCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.zerobase.cafebom.exception.ErrorCode.NOT_FOUND_OPTION_CATEGORY;

@Service
@Slf4j
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService{

    private final OptionRepository optionRepository;
    private final OptionCategoryRepository optionCategoryRepository;

    // 옵션 등록-jiyeon-23.98.30
    @Override
    public void addOption(OptionAddDto optionAddDto) {
        Integer optionCategoryId = optionAddDto.getOptionCategory();
        OptionCategory optionCategory = optionCategoryRepository.findById(optionCategoryId)
                    .orElseThrow(() -> new CustomException(NOT_FOUND_OPTION_CATEGORY));
            Option option = Option.builder()
                    .optionCategory(optionCategory)
                    .name(optionAddDto.getName())
                    .price(optionAddDto.getPrice())
                    .build();
            optionRepository.save(option);

    }
}
