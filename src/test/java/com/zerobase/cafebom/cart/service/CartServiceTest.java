package com.zerobase.cafebom.cart.service;

import static com.zerobase.cafebom.exception.ErrorCode.CART_IS_EMPTY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;


import com.zerobase.cafebom.cart.controller.form.CartAddForm;
import com.zerobase.cafebom.cart.domain.entity.Cart;
import com.zerobase.cafebom.cart.domain.entity.type.CartOrderStatus;
import com.zerobase.cafebom.cart.repository.CartRepository;
import com.zerobase.cafebom.cart.service.dto.CartProductDto;
import com.zerobase.cafebom.cartoption.repository.CartOptionRepository;
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.security.TokenProvider;
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

    @InjectMocks
    private CartService cartService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartOptionRepository cartOptionRepository;
    @Mock
    private OptionRepository optionRepository;
    @Mock
    private TokenProvider tokenProvider;

    String token = "Bearer token";

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
        .count(2)
        .build();

    CartAddForm cartAddForm = CartAddForm.builder()
        .optionIdList(List.of())
        .count(10)
        .productId(product.getId())
        .cartOrderStatus(CartOrderStatus.BEFORE_ORDER)
        .build();

    @BeforeEach
    public void setUp() {
        // given
        given(tokenProvider.getId(token)).willReturn(member.getId());
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
    }

    @Test
    @DisplayName("멤버가 존재하지 않는 경우 카트 수정 성공 케이스")
    public void successModifyCartMemberNotExists() {
        // given
        given(memberRepository.findById(member.getId())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> cartService.modifyCart(token, cartAddForm))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.MEMBER_NOT_EXISTS.getMessage());
    }

    @Test
    @DisplayName("상품이 존재하지 않는 경우 카트 수정 성공 케이스")
    public void successModifyCartProductNotExists() {
        // given
        given(productRepository.findById(cartAddForm.getProductId())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> cartService.modifyCart(token, cartAddForm))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.PRODUCT_NOT_EXISTS.getMessage());
    }

    @Test
    @DisplayName("카트 수정 성공 케이스")
    public void successModifyCart() {
        // given
        given(productRepository.findById(cartAddForm.getProductId())).willReturn(
            Optional.of(product)); // 상품이 존재한다고 가정
        given(cartRepository.save(any(Cart.class))).willReturn(cart);
        given(cartRepository.findByMemberAndProduct(member, product)).willReturn(List.of(cart));

        // when
        List<CartProductDto> result = cartService.modifyCart(token, cartAddForm);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("카트 삭제 성공 케이스")
    public void successRemoveCart() {
        // given
        given(productRepository.findById(cartAddForm.getProductId())).willReturn(
            Optional.of(product));
        given(cartRepository.save(any(Cart.class))).willReturn(cart);
        given(cartRepository.findByMemberAndProduct(member, product)).willReturn(List.of(cart));

        // when
        List<CartProductDto> result = cartService.removeCart(token, cartAddForm);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("멤버가 존재하지 않는 경우 카트 삭제 성공 케이스")
    public void successRemoveCartMemberNotExists() {
        // given
        given(memberRepository.findById(member.getId())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> cartService.removeCart(token, cartAddForm))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.MEMBER_NOT_EXISTS.getMessage());
        verify(cartRepository, times(0)).deleteById(cart.getId());
    }

    @Test
    @DisplayName("상품이 존재하지 않는 경우 카트 삭제 성공 케이스")
    public void successRemoveCartProductNotExists() {
        // given
        given(productRepository.findById(cartAddForm.getProductId())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> cartService.removeCart(token, cartAddForm))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.PRODUCT_NOT_EXISTS.getMessage());
        verify(cartRepository, times(0)).deleteById(cart.getId());
    }

    @Test
    @DisplayName("카트 조회 성공 케이스")
    public void successFindCart() {
        //given
        given(cartRepository.findByMember(member)).willReturn(List.of(cart));
        // when
        List<CartProductDto> result = cartService.findCart(token);
        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("장바구니가 비어있는 경우 카트 조회 예외 케이스")
    public void successFindCartEmptyCart() {
        //given
        given(cartRepository.findByMember(member)).willReturn(List.of());

        // when, then
        assertThatThrownBy(() -> cartService.findCart(token))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(CART_IS_EMPTY.getMessage());
    }
}