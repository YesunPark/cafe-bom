package com.zerobase.cafebom.admin.controller;

import com.zerobase.cafebom.optioncategory.service.OptionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminOptionCategoryController.class)
class AdminOptionCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OptionCategoryService optionCategoryService;

}