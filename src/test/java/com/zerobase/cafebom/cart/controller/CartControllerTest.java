package com.zerobase.cafebom.cart.controller;

import static com.zerobase.cafebom.security.Role.ROLE_USER;
import static com.zerobase.cafebom.type.SoldOutStatus.IN_STOCK;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.zerobase.cafebom.cart.service.CartService;
import com.zerobase.cafebom.cart.service.dto.CartListDto;
import com.zerobase.cafebom.member.domain.Member;
import com.zerobase.cafebom.option.domain.Option;
import com.zerobase.cafebom.optioncategory.domain.OptionCategory;
import com.zerobase.cafebom.product.domain.Product;
import com.zerobase.cafebom.productcategory.domain.ProductCategory;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private TokenProvider tokenProvider;

    static String TOKEN = "bearer token";

    @BeforeEach
    private void beforeEach() {
        given(tokenProvider.getId(TOKEN)).willReturn(1L);
    }

    // wooyoung-23.09.05
    @Test
    @DisplayName("장바구니 목록 조회 성공")
    void successCartList() throws Exception {
        // given
        Member member = Member.builder()
            .password("password")
            .nickname("nickname")
            .phone("010-1234-5678")
            .email("abcde@fghij.com")
            .role(ROLE_USER)
            .build();

        ProductCategory coffee = ProductCategory.builder()
            .name("커피")
            .build();

        Product espresso = Product.builder()
            .productCategory(coffee)
            .name("에스프레소")
            .description("씁쓸한 에스프레소")
            .price(1500)
            .soldOutStatus(IN_STOCK)
            .picture("pictureEspresso")
            .build();

        OptionCategory iceAmount = OptionCategory.builder()
            .id(1)
            .name("얼음 양")
            .build();

        Option iceAmountOption1 = Option.builder()
            .id(1)
            .optionCategory(iceAmount)
            .build();

        Option iceAmountOption2 = Option.builder()
            .id(2)
            .optionCategory(iceAmount)
            .build();

        Option iceAmountOption3 = Option.builder()
            .id(3)
            .optionCategory(iceAmount)
            .build();

        List<Option> optionList = new ArrayList<>();

        optionList.add(iceAmountOption1);
        optionList.add(iceAmountOption2);
        optionList.add(iceAmountOption3);

        List<CartListDto> dtoList = new ArrayList<>();

        CartListDto dto = CartListDto.builder()
            .productId(espresso.getId())
            .productName(espresso.getName())
            .productPicture(espresso.getPicture())
            .productOptions(optionList)
            .productCount(3)
            .build();

        dtoList.add(dto);

        given(cartService.findCartList(TOKEN)).willReturn(dtoList);

        // when
        mockMvc.perform(get("/auth/cart")
                .header("Authorization", TOKEN))
            .andDo(print())
            .andExpect(jsonPath("$[0].productId").value(espresso.getId()))
            .andExpect(jsonPath("$[0].productName").value(espresso.getName()))
            .andExpect(jsonPath("$[0].productPicture").value(espresso.getPicture()))
            .andExpect(jsonPath("$[0].productOptions[0].id").value(optionList.get(0).getId()))
            .andExpect(jsonPath("$[0].productCount").value(3));
    }


}