package com.zerobase.cafebom.admin.service;

import com.zerobase.cafebom.admin.dto.AdminOptionForm;
import com.zerobase.cafebom.admin.dto.AdminOptionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminOptionService {

    // 옵션 등록-jiyeon-23.08.30
    void addOption(AdminOptionDto.Request optionAddDto);

    // 옵션 수정-jiyeon-23.08.30
    void modifyOption(Integer id, AdminOptionDto.Request request);

    // 옵션 삭제-jiyeon-23.08.30
    void removeOption(Integer id);

    // 옵션 전체 조회-jiyeon-23.08.30
    List<AdminOptionForm.Response> findAllOption();

    // 옵션Id별 조회-jiyeon-23.08.30
    AdminOptionForm.Response findByIdOption(Integer id);
}
