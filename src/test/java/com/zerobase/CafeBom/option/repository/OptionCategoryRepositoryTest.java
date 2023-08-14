package com.zerobase.CafeBom.option.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.zerobase.CafeBom.menu.domain.entity.Menu;
import com.zerobase.CafeBom.option.domain.entity.OptionCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OptionCategoryRepositoryTest {

    @Autowired
    private OptionCategoryRepository optionCategoryRepository;

    Menu menu = Menu.builder()
        .category("1")
        .name("testMenuName")
        .description("testDescription")
        .price(2000)
        .picture("test")
        .stock(false)
        .build();

    OptionCategory optionCategory = OptionCategory.builder()
        .menu(menu)
        .name("testOptionCategoryName")
        .detailOption("testDetailOption")
        .build();

    // wooyoung-23.08.14
    @Test
    @DisplayName("옵션 카테고리 등록")
    void save() {
        // when
        OptionCategory saved = optionCategoryRepository.save(optionCategory);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getMenu()).isEqualTo(optionCategory.getMenu());
        assertThat(saved.getName()).isEqualTo(optionCategory.getName());
        assertThat(saved.getDetailOption()).isEqualTo(optionCategory.getDetailOption());
    }

    // wooyoung-23.08.14
    @Test
    @DisplayName("단건 조회 - 옵션 카테고리 id")
    void findById() {
        // given
        OptionCategory saved = optionCategoryRepository.save(optionCategory);

        // when
        OptionCategory found = optionCategoryRepository.findById(saved.getId())
            .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        // then
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getMenu()).isEqualTo(saved.getMenu());
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getDetailOption()).isEqualTo(saved.getDetailOption());
    }

    // wooyoung-23.08.14
    @Test
    @DisplayName("옵션 카테고리 업데이트 - 세부 옵션")
    void update() {
        // given
        OptionCategory saved = optionCategoryRepository.save(optionCategory);

        // when
        OptionCategory found = optionCategoryRepository.findById(saved.getId())
            .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
        found.setDetailOption("testDescription");

        // then
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getMenu()).isEqualTo(saved.getMenu());
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getDetailOption()).isEqualTo(saved.getDetailOption());

    }
}