package com.zerobase.CafeBom.menu.repository;

import com.zerobase.CafeBom.menu.domain.entity.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@DataJpaTest
class MenuRepositoryTest {

    @Autowired
    MenuRepository menuRepository;

    byte[] sampleImageData = "SampleImageContent".getBytes();

    @AfterEach
    void afterEach() {
        menuRepository.deleteAll();
        menuRepository.flush();
    }

    Menu menu = Menu.builder()
            .categoryId(1)
            .menuName("아메리카노")
            .description("아메리카노")
            .price(2800)
            .menuImg(sampleImageData)
            .createdDate(LocalDateTime.now())
            .build();

    // jiyeon-23.08.14
    @Test
    @DisplayName("메뉴 정보 등록")
    void saveTest() {
        // when
        Menu save = menuRepository.save(menu);

        // then
        assertThat(save.getId()).isNotNull();
        assertThat(save.getCategoryId()).isEqualTo(menu.getCategoryId());
        assertThat(save.getMenuName()).isEqualTo(menu.getMenuName());
        assertThat(save.getDescription()).isEqualTo(menu.getDescription());
        assertThat(save.getPrice()).isEqualTo(menu.getPrice());
        assertThat(save.getMenuImg()).isEqualTo(menu.getMenuImg());
        assertThat(save.getCreatedDate()).isEqualTo(menu.getCreatedDate());

    }

    // jiyeon-23.08.14
    @Test
    @DisplayName("메뉴 수정 확인")
    void updateTest() {
        // given
        Menu updateMenu = Menu.builder()
                .categoryId(2)
                .menuName("라떼")
                .description("라떼")
                .price(3000)
                .menuPicture(sampleImageData)
                .createdDate(menu.getCreatedDate())
                .modifiedDate(LocalDateTime.now())
                .build();

        // when
        Menu saveMenu = menuRepository.save(updateMenu);

        // then
        assertThat(saveMenu.getCategoryId()).isEqualTo(2);
        assertThat(saveMenu.getMenuName()).isEqualTo("라떼");
        assertThat(saveMenu.getDescription()).isEqualTo("라떼");
        assertThat(saveMenu.getCreatedDate()).isEqualTo(menu.getCreatedDate());
        assertThat(saveMenu.getModifiedDate())
                .isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));

    }

    // jiyeon-23.08.14
    @Test
    @DisplayName("메뉴Id 조회")
    void findByMenuId() {
        // when
        Menu saveMenu = menuRepository.save(menu);
        Long menuId = saveMenu.getId();

        // then
        Menu findMenuId = menuRepository.findById(menuId).orElse(null);
        assertThat(findMenuId).isNotNull();
        assertThat(findMenuId.getId()).isEqualTo(menuId);

    }

    // jiyeon-23.08.14
    @Test
    @DisplayName("메뉴 전체 조회")
    void findAll() {
        // given
        Menu menu2 = Menu.builder()
                .categoryId(2)
                .menuName("라떼")
                .description("라떼")
                .price(3800)
                .menuPicture(sampleImageData)
                .createdDate(LocalDateTime.now())
                .build();

        Menu saveMenu1 = menuRepository.save(menu);
        Menu saveMenu2 = menuRepository.save(menu2);

        // when
        List<Menu> findAllMenu = menuRepository.findAll();

        // then
        assertThat(findAllMenu.size()).isEqualTo(2);
        assertThat(findAllMenu.get(0).getCategoryId()).isEqualTo(saveMenu1.getCategoryId());
        assertThat(findAllMenu.get(1).getCategoryId()).isEqualTo(saveMenu2.getCategoryId());
        assertThat(findAllMenu.get(0).getMenuName()).isEqualTo(saveMenu1.getMenuName());
        assertThat(findAllMenu.get(1).getMenuName()).isEqualTo(saveMenu2.getMenuName());
        assertThat(findAllMenu.get(0).getDescription()).isEqualTo(saveMenu1.getDescription());
        assertThat(findAllMenu.get(1).getDescription()).isEqualTo(saveMenu2.getDescription());
        assertThat(findAllMenu.get(0).getPrice()).isEqualTo(saveMenu1.getPrice());
        assertThat(findAllMenu.get(1).getPrice()).isEqualTo(saveMenu2.getPrice());
        assertThat(findAllMenu.get(0).getMenuPicture()).isEqualTo(saveMenu1.getMenuPicture());
        assertThat(findAllMenu.get(1).getMenuPicture()).isEqualTo(saveMenu2.getMenuPicture());
        assertThat(findAllMenu.get(0).getCreatedDate()).isEqualTo(saveMenu1.getCreatedDate());
        assertThat(findAllMenu.get(1).getCreatedDate()).isEqualTo(saveMenu2.getCreatedDate());

    }

    // jiyeon-23.08.14
    @Test
    @DisplayName("메뉴 삭제 확인 - 메뉴id")
    void deleteMenu(){
        // given
        Menu saveMenu = menuRepository.save(menu);
        // when
        menuRepository.delete(saveMenu);
        // then
        assertThat(menuRepository.findById(saveMenu.getId())).isEmpty();

    }
}