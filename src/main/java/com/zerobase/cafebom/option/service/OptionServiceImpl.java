package com.zerobase.cafebom.option.service;

import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.option.domain.type.OptionDto;
import com.zerobase.cafebom.option.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService{

    private final OptionRepository optionRepository;


    // 옵션 삭제-jiyeon-23.08.30
    @Override
    public boolean removeOption(Integer id) {
        Optional<Option> optionId = optionRepository.findById(id);
        if (optionId.isPresent()) {
            optionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 옵션 전체 조회-jiyeon-23.08.30
    @Override
    public List<OptionDto> findAllOption() {
        List<Option> optionList = optionRepository.findAll();
        List<OptionDto> optionDtoList = optionList.stream()
                .map(OptionDto::from)
                .collect(Collectors.toList());
        return optionDtoList;
    }

    // 옵션Id별 조회-jiyeon-23.08.30
    @Override
    public OptionDto findByIdOption(Integer id) {
        Optional<Option> optionId = optionRepository.findById(id);
        Option option = optionId.get();
        OptionDto optionDto = OptionDto.from(option);
        return optionDto;
    }
}
