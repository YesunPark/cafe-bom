package com.zerobase.cafebom.option.service;

import com.zerobase.cafebom.option.controller.form.OptionAddForm;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.option.service.dto.OptionAddDto;
import com.zerobase.cafebom.optioncategory.domain.entity.OptionCategory;
import com.zerobase.cafebom.optioncategory.repository.OptionCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService{

    private final OptionRepository optionRepository;
    private final OptionCategoryRepository optionCategoryRepository;

    // 옵션 등록jiyeon-23.98.30
    @Override
    public OptionAddDto addOption(OptionAddForm optionAddForm) {
            Integer optionCategoryId = optionAddForm.getOptionCategory();
            OptionCategory optionCategory = optionCategoryRepository.findById(optionCategoryId)
                    .orElseThrow(() -> new IllegalArgumentException());

            Option option = Option.builder()
                    .optionCategory(optionCategory)
                    .name(optionAddForm.getName())
                    .price(optionAddForm.getPrice())
                    .build();
            optionRepository.save(option);
            OptionAddDto optionAddDto = OptionAddDto.from(option);
            return optionAddDto;
    }
}
