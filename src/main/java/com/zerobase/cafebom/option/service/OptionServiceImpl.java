package com.zerobase.cafebom.option.service;

import com.zerobase.cafebom.option.controller.form.OptionModifyForm;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.optioncategory.domain.entity.OptionCategory;
import com.zerobase.cafebom.optioncategory.repository.OptionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;
    private final OptionCategoryRepository optionCategoryRepository;

    // 옵션 수정-jiyeon-23.08.30
    @Override
    public boolean modifyOption(Integer id, OptionModifyForm form) {
        Integer optionCategoryId = form.getOptionCategory();
        OptionCategory optionCategory = optionCategoryRepository.findById(optionCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Option Category not found"));

        Optional<Option> optionToUpdate = optionRepository.findById(id);

        if (optionToUpdate.isPresent()) {
            Option option = optionToUpdate.get();

            Option modifyOption = Option.builder()
                    .optionCategory(optionCategory)
                    .name(form.getName())
                    .price(form.getPrice())
                    .build();

            option = modifyOption.toBuilder()
                    .id(option.getId())
                    .build();

            optionRepository.save(option);
            return true;
        } else {
            return false;
        }
    }
}

