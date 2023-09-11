package com.zerobase.cafebom.cart.domain;

import com.zerobase.cafebom.member.domain.MemberRepository;
import com.zerobase.cafebom.product.domain.ProductRepository;
import com.zerobase.cafebom.productcategory.domain.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

//    @Test
//    @DisplayName("사용자 장바구니 목록 찾기 성공")
//    void findAllByMember() {
//        // given
//        Member member = Member.builder()
//            .password("password")
//            .nickname("nickname")
//            .phone("010-1234-5678")
//            .email("abcde@fghij.com")
//            .role(ROLE_USER)
//            .build();
//
//        memberRepository.save(member);
//
//        ProductCategory coffee = ProductCategory.builder()
//            .name("커피")
//            .build();
//
//        productCategoryRepository.save(coffee);
//
//        Product espresso = Product.builder()
//            .productCategory(coffee)
//            .name("에스프레소")
//            .description("씁쓸한 에스프레소")
//            .price(1500)
//            .soldOutStatus(IN_STOCK)
//            .picture("pictureEspresso")
//            .build();
//
//        productRepository.save(espresso);
//
//        Cart cart1 = Cart.builder()
//            .member(member)
//            .product(espresso)
//            .productCount(2)
//            .status(BEFORE_ORDER)
//            .build();
//
//        Cart cart2 = Cart.builder()
//            .member(member)
//            .product(espresso)
//            .productCount(2)
//            .status(CartOrderStatus.WAITING_ACCEPTANCE)
//            .build();
//
//        Cart cart3 = Cart.builder()
//            .member(member)
//            .product(espresso)
//            .productCount(2)
//            .status(BEFORE_ORDER)
//            .build();
//
//        cartRepository.save(cart1);
//        cartRepository.save(cart2);
//        cartRepository.save(cart3);
//
//        // when
//        List<Cart> cartList = cartRepository.findAllByMember(member);
//
//        // then
//        Assertions.assertThat(cartList.size()).isEqualTo(3);
//    }
}