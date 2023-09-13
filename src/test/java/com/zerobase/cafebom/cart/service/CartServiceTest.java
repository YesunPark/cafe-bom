package com.zerobase.cafebom.cart.service;

import static com.zerobase.cafebom.security.Role.ROLE_USER;
import static com.zerobase.cafebom.type.CartOrderStatus.BEFORE_ORDER;
import static com.zerobase.cafebom.type.CartOrderStatus.WAITING_ACCEPTANCE;
import static com.zerobase.cafebom.type.SoldOutStatus.IN_STOCK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.zerobase.cafebom.cart.domain.Cart;
import com.zerobase.cafebom.cart.domain.CartRepository;
import com.zerobase.cafebom.cart.dto.CartListDto;
import com.zerobase.cafebom.cartoption.domain.CartOption;
import com.zerobase.cafebom.cartoption.domain.CartOptionRepository;
import com.zerobase.cafebom.member.domain.Member;
import com.zerobase.cafebom.member.domain.MemberRepository;
import com.zerobase.cafebom.option.domain.Option;
import com.zerobase.cafebom.optioncategory.domain.OptionCategory;
import com.zerobase.cafebom.product.domain.Product;
import com.zerobase.cafebom.productcategory.domain.ProductCategory;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartOptionRepository cartOptionRepository;

    @InjectMocks
    private CartService cartService;

    static String TOKEN = "bearer token";

    @BeforeEach
    private void beforeEach() {
        given(tokenProvider.getId(TOKEN)).willReturn(1L);
    }

    // wooyoung-23.09.14
    @Test
    @DisplayName("장바구니 목록 조회 성공")
    void successFindCartList() {
        // given
        Member member = Member.builder()
            .password("password")
            .nickname("nickname")
            .phone("010-1234-5678")
            .email("abcde@fghij.com")
            .role(ROLE_USER)
            .build();

        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

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

        Cart cart1 = Cart.builder()
            .id(1L)
            .member(member)
            .product(espresso)
            .productCount(1)
            .status(BEFORE_ORDER)
            .build();

        Cart cart2 = Cart.builder()
            .id(2L)
            .member(member)
            .product(espresso)
            .productCount(2)
            .status(WAITING_ACCEPTANCE)
            .build();

        Cart cart3 = Cart.builder()
            .id(3L)
            .member(member)
            .product(espresso)
            .productCount(3)
            .status(BEFORE_ORDER)
            .build();

        List<Cart> cartList = new ArrayList<>();

        cartList.add(cart1);
        cartList.add(cart2);
        cartList.add(cart3);

        given(cartRepository.findAllByMemberAndStatus(1L, BEFORE_ORDER)).willReturn(cartList);

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

        CartOption cartOption1 = CartOption.builder()
            .id(1L)
            .cart(cart1)
            .option(iceAmountOption1)
            .build();

        CartOption cartOption2 = CartOption.builder()
            .id(2L)
            .cart(cart2)
            .option(iceAmountOption2)
            .build();

        CartOption cartOption3 = CartOption.builder()
            .id(3L)
            .cart(cart3)
            .option(iceAmountOption3)
            .build();

        List<CartOption> cartOptions1 = new ArrayList<>();
        List<CartOption> cartOptions2 = new ArrayList<>();
        List<CartOption> cartOptions3 = new ArrayList<>();

        cartOptions1.add(cartOption1);
        cartOptions2.add(cartOption2);
        cartOptions3.add(cartOption3);

        given(cartOptionRepository.findAllByCart(cart1)).willReturn(cartOptions1);
        given(cartOptionRepository.findAllByCart(cart2)).willReturn(cartOptions2);
        given(cartOptionRepository.findAllByCart(cart3)).willReturn(cartOptions3);

        // when
        List<CartListDto> cartListDtos = cartService.findCartList(TOKEN);

        // then
        assertThat(cartListDtos.size()).isEqualTo(3);
        assertThat(cartListDtos.get(0).getCartId()).isEqualTo(cart1.getId());
        assertThat(cartListDtos.get(0).getProductId()).isEqualTo(espresso.getId());
        assertThat(cartListDtos.get(0).getProductName()).isEqualTo(espresso.getName());
        assertThat(cartListDtos.get(0).getProductPicture()).isEqualTo(espresso.getPicture());
        assertThat(cartListDtos.get(0).getCartListOptionDtos().get(0).getOptionId()).isEqualTo(iceAmountOption1.getId());
        assertThat(cartListDtos.get(0).getProductCount()).isEqualTo(cart1.getProductCount());
    }

}