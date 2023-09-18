package com.zerobase.cafebom.cart.service;

import static com.zerobase.cafebom.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.OPTION_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.type.CartOrderStatus.BEFORE_ORDER;

import com.zerobase.cafebom.cart.controller.form.CartAddForm;
import com.zerobase.cafebom.cart.domain.Cart;
import com.zerobase.cafebom.cart.domain.CartRepository;
import com.zerobase.cafebom.cart.dto.CartListDto;
import com.zerobase.cafebom.cart.service.dto.CartProductDto;
import com.zerobase.cafebom.cartoption.domain.CartOption;
import com.zerobase.cafebom.cartoption.domain.CartOptionRepository;
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.member.domain.Member;
import com.zerobase.cafebom.member.domain.MemberRepository;
import com.zerobase.cafebom.option.domain.Option;
import com.zerobase.cafebom.option.domain.OptionRepository;
import com.zerobase.cafebom.product.domain.Product;
import com.zerobase.cafebom.product.domain.ProductRepository;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    private final CartOptionRepository cartOptionRepository;

    private final TokenProvider tokenProvider;

    private final ProductRepository productRepository;

    private final MemberRepository memberRepository;

    private final OptionRepository optionRepository;

    // 장바구니 목록 조회-wooyoung-23.09.03
    public List<CartListDto> findCartList(String token) {
        Long memberId = tokenProvider.getId(token);

        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        List<Cart> cartList = cartRepository.findAllByMemberAndStatus(member, BEFORE_ORDER);

        List<CartListDto> cartListDtoList = new ArrayList<>();

        for (Cart cart : cartList) {
            List<CartOption> allByCart = cartOptionRepository.findAllByCart(cart);

            List<Option> optionList = new ArrayList<>();

            for (CartOption cartOption : allByCart) {
                optionList.add(cartOption.getOption());
            }

            Product product = cart.getProduct();

            CartListDto cartListDto = CartListDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productPicture(product.getPicture())
                .productOptions(optionList)
                .productCount(cart.getProductCount())
                .build();

            cartListDtoList.add(cartListDto);
        }

        return cartListDtoList;
    }

    // 장바구니에 상품 삭제-youngseon-23.09.10
    public List<CartProductDto> removeCart(String token, CartAddForm cartAddForm) {

        Long userId = tokenProvider.getId(token);

        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        Product product = productRepository.findById(cartAddForm.getProductId())
            .orElseThrow(() -> new CustomException(PRODUCT_NOT_EXISTS));

        List<Cart> cartList = cartRepository.findByMemberAndProduct(member, product);

        if (cartList.size() == 0) {
            throw new CustomException(PRODUCT_NOT_EXISTS);
        }

        if (cartList.size() > 0) {

            Boolean result = false;

            Integer count = 0;

            for (Cart otherCart : cartList) {

                List<Integer> optionIdList = from(otherCart);

                Collections.sort(optionIdList);

                List<Integer> optionIdCopyList = cartAddForm.getOptionIdList().stream()
                    .sorted()
                    .collect(Collectors.toList());

                if (optionIdList.size() == cartAddForm.getOptionIdList().size()) {
                    result = compare(optionIdList, optionIdCopyList);
                    if (result) {
                        cartOptionRepository.deleteAllByCart(otherCart);
                        cartRepository.deleteById(otherCart.getId());
                        count++;
                    }
                }
            }
            if (count == 0) {
                throw new CustomException(PRODUCT_NOT_EXISTS);
            }
        }

        List<Cart> carts = cartRepository.findByMember(member);

        List<CartProductDto> cartProductDtoList = new ArrayList<>();

        for (Cart otherCart : carts) {

            CartProductDto cartProductDto = CartProductDto.from(otherCart);

            List<CartOption> cartOptionList = cartOptionRepository.findByCart(otherCart);

            for (CartOption cartOption : cartOptionList) {
                cartProductDto.addOptionId(cartOption.getOption().getId());
            }

            cartProductDtoList.add(cartProductDto);
        }

        return cartProductDtoList;
    }

    // 장바구니 상품 수량 변경- youngseon-23.09.10
    public List<CartProductDto> modifyCart(String token, CartAddForm cartAddForm) {

        Long userId = tokenProvider.getId(token);

        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        Product product = productRepository.findById(cartAddForm.getProductId())
            .orElseThrow(() -> new CustomException(PRODUCT_NOT_EXISTS));

        List<Cart> cartList = cartRepository.findByMemberAndProduct(member, product);

        if (cartList.size() == 0) {

            throw new CustomException(PRODUCT_NOT_EXISTS);
        }

        if (cartList.size() > 0) {

            Boolean result = false;

            Integer count = 0;

            for (Cart otherCart : cartList) {

                List<Integer> optionIdList = from(otherCart);

                Collections.sort(optionIdList);

                List<Integer> optionIdCopyList = cartAddForm.getOptionIdList().stream()
                    .sorted()
                    .collect(Collectors.toList());

                if (optionIdList.size() == cartAddForm.getOptionIdList().size()) {
                    result = compare(optionIdList, optionIdCopyList);
                    if (result) {
                        otherCart.setProductCount(cartAddForm.getCount());
                        cartRepository.save(otherCart);
                        count++;
                    }
                }
            }
            if (count == 0) {
                throw new CustomException(PRODUCT_NOT_EXISTS);
            }
        }
        List<Cart> carts = cartRepository.findByMember(member);

        List<CartProductDto> cartProductDtoList = new ArrayList<>();

        for (Cart otherCart : carts) {

            CartProductDto cartProductDto = CartProductDto.from(otherCart);

            List<CartOption> cartOptionList = cartOptionRepository.findByCart(otherCart);

            for (CartOption cartOption : cartOptionList) {
                cartProductDto.addOptionId(cartOption.getOption().getId());
            }
            cartProductDtoList.add(cartProductDto);
        }
        return cartProductDtoList;
    }

    // 장바구니에 상품 넣기-youngseon-23.09.12
    public List<CartProductDto> saveCart(String token, CartAddForm cartAddForm) {

        Long userId = tokenProvider.getId(token);

        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        Product product = productRepository.findById(cartAddForm.getProductId())
            .orElseThrow(() -> new CustomException(PRODUCT_NOT_EXISTS));

        List<Cart> cartList = cartRepository.findByMemberAndProduct(member, product);


        if (cartList.size() == 0){

            Cart cart = Cart.createCart(member, product, cartAddForm.getCount(),
                cartAddForm.getCartOrderStatus());

            cartRepository.save(cart);

            for (Integer optionId : cartAddForm.getOptionIdList()) {

                Option option = optionRepository.findById(optionId)
                    .orElseThrow(() -> new CustomException(OPTION_NOT_EXISTS));

                CartOption cartOption = CartOption.createCartOption(cart, option);

                cartOptionRepository.save(cartOption);

            }

        }else if (cartList.size() > 0) {

            Boolean result = false;

            Integer count = 0;

            for (Cart otherCart : cartList) {

                List<Integer> optionIdList = from(otherCart);

                Collections.sort(optionIdList);

                List<Integer> optionIdCopyList = cartAddForm.getOptionIdList().stream()
                    .sorted()
                    .collect(Collectors.toList());

                if (optionIdList.size() == cartAddForm.getOptionIdList().size()) {
                    result = compare(optionIdList, optionIdCopyList);

                    if (result) {
                        otherCart.addProductCount(cartAddForm.getCount());
                        cartRepository.save(otherCart);
                        count++;
                    }
                }
            }

            if (count == 0) {
                Cart cart = Cart.createCart(member, product, cartAddForm.getCount(),
                    cartAddForm.getCartOrderStatus());

                cartRepository.save(cart);

                for (Integer optionId : cartAddForm.getOptionIdList()) {

                    Option option = optionRepository.findById(optionId)
                        .orElseThrow(() -> new CustomException(OPTION_NOT_EXISTS));

                    CartOption cartOption = CartOption.createCartOption(cart, option);

                    cartOptionRepository.save(cartOption);

                }
            }

        }

        List<Cart> carts = cartRepository.findByMember(member);

        List<CartProductDto> cartProductDtoList = new ArrayList<>();

        for (Cart otherCart : carts) {

            CartProductDto cartProductDto = CartProductDto.from(otherCart);

            List<CartOption> cartOptionList = cartOptionRepository.findByCart(otherCart);

            for (CartOption cartOption : cartOptionList) {
                cartProductDto.addOptionId(cartOption.getOption().getId());
            }

            cartProductDtoList.add(cartProductDto);
        }


        return cartProductDtoList;
    }


    // Cart 객체를 사용해서 optionId들을 리스트에 저장-youngseon-23.09.10
    private List<Integer> from(Cart cart) {

        List<CartOption> cartOptionList = cartOptionRepository.findByCart(cart);

        List<Integer> optionIdList = new ArrayList<>();

        for (CartOption cartOption : cartOptionList) {
            optionIdList.add(cartOption.getOption().getId());
        }

        return optionIdList;
    }

    // optionId 리스트가 동일한지 비교-youngseon-23.09.10
    private boolean compare(List<Integer> optionIdList1, List<Integer> optionIdList2) {

        String s1 = optionIdList1.toString();

        String s2 = optionIdList2.toString();

        return s1.equals(s2);
    }
}
