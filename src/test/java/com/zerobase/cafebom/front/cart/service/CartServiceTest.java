package com.zerobase.cafebom.front.cart.service;

import static com.zerobase.cafebom.common.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.common.config.security.Role.ROLE_USER;
import static com.zerobase.cafebom.common.type.CartOrderStatus.BEFORE_ORDER;
import static com.zerobase.cafebom.common.type.CartOrderStatus.WAITING_ACCEPTANCE;
import static com.zerobase.cafebom.common.type.SoldOutStatus.IN_STOCK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.cafebom.front.cart.dto.CartAddForm;
import com.zerobase.cafebom.front.cart.domain.Cart;
import com.zerobase.cafebom.front.cart.domain.CartRepository;
import com.zerobase.cafebom.front.cart.dto.CartListDto;
import com.zerobase.cafebom.front.cart.service.impl.CartService;
import com.zerobase.cafebom.front.cart.dto.CartProductDto;
import com.zerobase.cafebom.front.cart.domain.CartOption;
import com.zerobase.cafebom.front.cart.domain.CartOptionRepository;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.common.exception.ErrorCode;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.member.domain.MemberRepository;
import com.zerobase.cafebom.front.product.domain.Option;
import com.zerobase.cafebom.front.product.domain.OptionRepository;
import com.zerobase.cafebom.front.product.domain.OptionCategory;
import com.zerobase.cafebom.front.product.domain.Product;
import com.zerobase.cafebom.front.product.domain.ProductRepository;
import com.zerobase.cafebom.front.product.domain.ProductCategory;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CartServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private TokenProvider tokenProvider;

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

    // wooyoung-23.09.18
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
            .quantity(1)
            .status(BEFORE_ORDER)
            .build();

        Cart cart2 = Cart.builder()
            .id(2L)
            .member(member)
            .product(espresso)
            .quantity(2)
            .status(WAITING_ACCEPTANCE)
            .build();

        Cart cart3 = Cart.builder()
            .id(3L)
            .member(member)
            .product(espresso)
            .quantity(3)
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
        assertThat(cartListDtos.get(0).getCartListOptionDtos().get(0).getOptionId()).isEqualTo(
            iceAmountOption1.getId());
        assertThat(cartListDtos.get(0).getQuantity()).isEqualTo(cart1.getQuantity());
    }

    // wooyoung-23.09.14
    @Test
    @DisplayName("장바구니 목록 조회 실패 - 존재하지 않는 사용자")
    void failFindCartListMemberNotExists() {
        // given
        given(memberRepository.findById(member.getId())).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> cartService.findCartList(TOKEN))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(MEMBER_NOT_EXISTS.getMessage());
    }

    Member member = Member.builder()
        .id(1L)
        .build();
    Product product = Product.builder()
        .id(1)
        .build();
    Cart cart = Cart.builder()
        .id(1L)
        .member(member)
        .product(product)
        .quantity(2)
        .build();

    CartAddForm cartAddForm = CartAddForm.builder()
        .optionIdList(List.of())
        .quantity(10)
        .productId(product.getId())
        .cartOrderStatus(BEFORE_ORDER)
        .build();

    @BeforeEach
    public void setUp() {
        // given
        given(tokenProvider.getId(TOKEN)).willReturn(member.getId());
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
    }

    // youngseon-23.09.11
    @Test
    @DisplayName("카트 수정 실패 - 존재하지 않는 회원")
    public void failModifyCartMemberNotExists() {
        // given
        given(memberRepository.findById(member.getId())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> cartService.modifyCart(TOKEN, cartAddForm))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(MEMBER_NOT_EXISTS.getMessage());
    }

    // youngseon-23.09.11
    @Test
    @DisplayName("카트 수정 실패 - 상품이 존재하지 않음")
    public void failModifyCartProductNotExists() {
        // given
        given(productRepository.findById(cartAddForm.getProductId())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> cartService.modifyCart(TOKEN, cartAddForm))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.PRODUCT_NOT_EXISTS.getMessage());
    }

    // youngseon-23.09.11
    @Test
    @DisplayName("카트 수정 성공")
    public void successModifyCart() {
        // given
        given(productRepository.findById(cartAddForm.getProductId())).willReturn(
            Optional.of(product));
        given(cartRepository.save(any(Cart.class))).willReturn(cart);
        given(cartRepository.findByMemberAndProduct(member, product)).willReturn(List.of(cart));

        // when
        List<CartProductDto> result = cartService.modifyCart(TOKEN, cartAddForm);

        // then
        assertThat(result).isNotNull();
    }

    // youngseon-23.09.11
    @Test
    @DisplayName("카트 삭제 성공")
    public void successRemoveCart() {
        // given
        given(productRepository.findById(cartAddForm.getProductId())).willReturn(
            Optional.of(product));
        given(cartRepository.save(any(Cart.class))).willReturn(cart);
        given(cartRepository.findByMemberAndProduct(member, product)).willReturn(List.of(cart));

        // when
        List<CartProductDto> result = cartService.removeCart(TOKEN, cartAddForm);

        // then
        assertThat(result).isNotNull();
    }

    // youngseon-23.09.11
    @Test
    @DisplayName("카트 삭제 실패 - 멤버가 존재하지 않음")
    public void failRemoveCartMemberNotExists() {
        // given
        given(memberRepository.findById(member.getId())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> cartService.removeCart(TOKEN, cartAddForm))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(MEMBER_NOT_EXISTS.getMessage());
        verify(cartRepository, times(0)).deleteById(cart.getId());
    }

    // youngseon-23.09.11
    @Test
    @DisplayName("카트 삭제 실패 - 상품이 존재하지 않음")
    public void failRemoveCartProductNotExists() {
        // given
        given(productRepository.findById(cartAddForm.getProductId())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> cartService.removeCart(TOKEN, cartAddForm))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.PRODUCT_NOT_EXISTS.getMessage());
        verify(cartRepository, times(0)).deleteById(cart.getId());
    }

    // youngseon-23.09.12
    @Test
    @DisplayName("장바구니에 새로운 상품 추가 성공")
    public void successSaveCart() {
        // given
        given(tokenProvider.getId(TOKEN)).willReturn(member.getId());
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        given(productRepository.findById(cartAddForm.getProductId())).willReturn(
            Optional.of(product));
        given(cartRepository.findByMemberAndProduct(member, product)).willReturn(
            Collections.emptyList());

        // when
        List<CartProductDto> result = cartService.saveCart(TOKEN, cartAddForm);

        // then
        assertThat(result).isNotNull();

    }

}