package com.zerobase.cafebom.option.service;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.option.service.dto.OptionModifyDto;
import com.zerobase.cafebom.optioncategory.domain.entity.OptionCategory;
import com.zerobase.cafebom.optioncategory.repository.OptionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

@Service
@Slf4j
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService{

    private final OptionRepository optionRepository;
    private final OptionCategoryRepository optionCategoryRepository;

    // 옵션 등록-jiyeon-23.08.30
    @Override
    public void addOption(OptionAddDto optionAddDto) {
        Integer optionCategoryId = optionAddDto.getOptionCategoryId();
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
    // 옵션 수정-jiyeon-23.08.30
    @Override
    public void modifyOption(Integer id, OptionModifyDto.Request request) {
        Integer optionCategoryId = request.getOptionCategoryId();
        OptionCategory optionCategory = optionCategoryRepository.findById(optionCategoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OPTION_CATEGORY));

        Option optionId = optionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OPTION));

        Option modifyOption = Option.builder()
                .optionCategory(optionCategory)
                .name(request.getName())
                .price(request.getPrice())
                .build();

        optionRepository.save(modifyOption.toBuilder()
                .id(optionId.getId())
                .build());

    }
}

