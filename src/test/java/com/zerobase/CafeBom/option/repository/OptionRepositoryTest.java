package com.zerobase.CafeBom.option.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.zerobase.CafeBom.menu.domain.entity.Menu;
import com.zerobase.CafeBom.option.domain.entity.Option;
import com.zerobase.CafeBom.option.domain.entity.OptionCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OptionRepositoryTest {

    @Autowired
    private OptionRepository optionRepository;

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

    Option option = Option.builder()
        .optionCategory(optionCategory)
        .name("testName")
        .additionalPrice(300)
        .build();

    // wooyoung-23.08.14
    @Test
    @DisplayName("옵션 등록")
    void save() {
        // when
        Option saved = optionRepository.save(option);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getOptionCategory()).isEqualTo(option.getOptionCategory());
        assertThat(saved.getName()).isEqualTo(option.getName());
        assertThat(saved.getAdditionalPrice()).isEqualTo(option.getAdditionalPrice());
    }

    // wooyoung-23.08.14
    @Test
    @DisplayName("단건 조회 - 옵션 id")
    void findById() {
        // given
        Option saved = optionRepository.save(option);

        // when
        Option found = optionRepository.findById(saved.getId())
            .orElseThrow(() -> new RuntimeException("OPTION_NOT_FOUND"));

        // then
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getOptionCategory()).isEqualTo(saved.getOptionCategory());
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getAdditionalPrice()).isEqualTo(saved.getAdditionalPrice());
    }

    // wooyoung-23.08.14
    @Test
    @DisplayName("옵션 업데이트 - 추가 금액 변경")
    void update() {
        // given
        Option saved = optionRepository.save(option);

        // when
        Option found = optionRepository.findById(saved.getId())
            .orElseThrow(() -> new RuntimeException("OPTION_NOT_FOUND"));
        found.setAdditionalPrice(400);

        // then
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getOptionCategory()).isEqualTo(saved.getOptionCategory());
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getAdditionalPrice()).isEqualTo(saved.getAdditionalPrice());

    }

}