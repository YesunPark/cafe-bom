package com.zerobase.cafebom.admin.service;

import com.zerobase.cafebom.admin.dto.AdminOptionCategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminOptionCategoryService {

    // jiyeon-23.09.09
    void removeOptionCategory(Integer optionCategoryId);

    // jiyeon-23.09.09
    List<AdminOptionCategoryDto.Response> findOptionCategoryList();

    // jiyeon-23.09.09
    AdminOptionCategoryDto.Response findOptionCategoryListById(Integer optionCategoryId);
}
