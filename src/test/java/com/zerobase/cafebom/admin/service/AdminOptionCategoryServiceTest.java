package com.zerobase.cafebom.admin.service;

import com.zerobase.cafebom.admin.dto.AdminOptionCategoryForm;
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.optioncategory.domain.OptionCategory;
import com.zerobase.cafebom.optioncategory.domain.OptionCategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class AdminOptionCategoryServiceTest {

    @Mock
    private OptionCategoryRepository optionCategoryRepository;

    @InjectMocks
    private AdminOptionCategoryServiceImpl optionCategoryService;

    // jiyeon-23.09.05
    @Test
    @DisplayName("관리자 옵션 카테고리 수정 성공")
    public void successModifyTest() {
        // given
        OptionCategory optionCategory = OptionCategory.builder()
                .id(1)
                .name("얼음")
                .build();

        AdminOptionCategoryForm.Request optionCategoryForm = AdminOptionCategoryForm.Request.builder()
                .name("사이즈")
                .build();

        when(optionCategoryRepository.findById(anyInt())).thenReturn(Optional.of(optionCategory));

        // when
        optionCategoryService.modifyOptionCategory(optionCategory.getId(), optionCategoryForm);

        // then
        verify(optionCategoryRepository, times(1)).findById(anyInt());
        verify(optionCategoryRepository, times(1)).save(any(OptionCategory.class));

        assertThat(optionCategory.getName()).isEqualTo("사이즈");
        assertThat(optionCategory.getId()).isEqualTo(1);

    }

    // jiyeon-23.09.05
    @Test
    @DisplayName("관리자 옵션 카테고리 수정 실패 - 존재하지 않는 옵션 카테고리")
    public void failOptionCategoryModify(){
        // given
        OptionCategory optionCategory = OptionCategory.builder()
                .id(1)
                .name("얼음")
                .build();

        AdminOptionCategoryForm.Request optionCategoryForm = AdminOptionCategoryForm.Request.builder()
                .name("사이즈")
                .build();

        when(optionCategoryRepository.findById(2)).thenReturn(Optional.empty());

        // when
        Throwable exception = Assertions.assertThrows(CustomException.class, () -> {
            optionCategoryService.modifyOptionCategory(2, optionCategoryForm);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("옵션 카테고리를 찾을 수 없습니다.");

    }
}