package com.zerobase.cafebom.admin.service;

import com.zerobase.cafebom.admin.dto.AdminOptionCategoryForm;
import org.springframework.stereotype.Service;

@Service
public interface AdminOptionCategoryService {

    // 관리자 옵션 카테고리 등록 -jiyeon-23.09.05
    void addOptionCategory(AdminOptionCategoryForm.Request optionCategoryFormRequest);

    // 관리자 옵션 카테고리 수정-jiyeon-23.09.05
    void modifyOptionCategory(Integer optionCategoryId, AdminOptionCategoryForm.Request optionCategoryForm);

@Service
public interface AdminOptionCategoryService {

    // jiyeon-23.09.09
    void removeOptionCategory(Integer optionCategoryId);

    // jiyeon-23.09.09
    List<AdminOptionCategoryDto.Response> findOptionCategoryList();

    // jiyeon-23.09.09
    AdminOptionCategoryDto.Response findOptionCategoryListById(Integer optionCategoryId);
}
