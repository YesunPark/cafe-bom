package com.zerobase.cafebom.admin.product;

import com.zerobase.cafebom.admin.product.dto.AdminOptionCategoryDto;
import com.zerobase.cafebom.admin.product.dto.AdminOptionCategoryForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminOptionCategoryService {

    // 관리자 옵션 카테고리 등록 -jiyeon-23.09.05
    void addOptionCategory(AdminOptionCategoryForm.Request optionCategoryFormRequest);

    // 관리자 옵션 카테고리 수정-jiyeon-23.09.05
    void modifyOptionCategory(Integer optionCategoryId, AdminOptionCategoryForm.Request optionCategoryForm);

    // jiyeon-23.09.09
    void removeOptionCategory(Integer optionCategoryId);

    // jiyeon-23.09.09
    List<AdminOptionCategoryDto.Response> findOptionCategoryList();

    // jiyeon-23.09.09
    AdminOptionCategoryDto.Response findOptionCategoryListById(Integer optionCategoryId);
}
