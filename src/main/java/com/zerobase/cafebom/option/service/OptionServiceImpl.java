package com.zerobase.cafebom.option.service;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.option.controller.form.OptionForm;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.option.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService{

    private final OptionRepository optionRepository;


    // 옵션 삭제-jiyeon-23.08.30
    @Override
    public void removeOption(Integer id) {
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OPTION_ID));
        optionRepository.deleteById(option.getId());
    }

    // 옵션 전체 조회-jiyeon-23.08.30
    @Override
    public List<OptionForm.Response> findAllOption() {
        List<Option> optionList = optionRepository.findAll();
        List<OptionForm.Response> optionDtoList = optionList.stream()
                .map(OptionForm.Response::from)
                .collect(Collectors.toList());
        return optionDtoList;
    }

    // 옵션Id별 조회-jiyeon-23.08.30
    @Override
    public OptionForm.Response findByIdOption(Integer id) {
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OPTION_ID));
        OptionForm.Response response = OptionForm.Response.from(option);
        return response;
    }
}
