package com.zerobase.cafebom.orders.service;

import static com.zerobase.cafebom.exception.ErrorCode.CART_IS_EMPTY;
import static com.zerobase.cafebom.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.OPTION_NOT_EXISTS;

import com.zerobase.cafebom.cart.domain.entity.Cart;
import com.zerobase.cafebom.cart.repository.CartRepository;
import com.zerobase.cafebom.cartoption.domain.entity.CartOption;
import com.zerobase.cafebom.cartoption.repository.CartOptionRepository;
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingStatus;
import com.zerobase.cafebom.orders.domain.type.OrdersReceiptStatus;
import com.zerobase.cafebom.orders.repository.OrdersRepository;
import com.zerobase.cafebom.orders.service.dto.OrdersAddDto;
import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import com.zerobase.cafebom.ordersproduct.repository.OrdersProductRepository;
import com.zerobase.cafebom.ordersproductoption.domain.entity.OrdersProductOption;
import com.zerobase.cafebom.ordersproductoption.repository.OrdersProductOptionRepository;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersService {

    private final MemberRepository memberRepository;

    private final CartRepository cartRepository;
    private final CartOptionRepository cartOptionRepository;
    private final OptionRepository optionRepository;

    private final OrdersRepository ordersRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final OrdersProductOptionRepository ordersProductOptionRepository;

    private final TokenProvider tokenProvider;

    // 주문 생성-yesun-23.08.31
    @Transactional
    public void addOrders(String token, OrdersAddDto.Request ordersAddDto) {
        Long userId = tokenProvider.getId(token);
        Member memberById = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        Orders savedOrders = ordersRepository.save(Orders.builder()
            .member(memberById)
            .payment(ordersAddDto.getPayment())
            .cookingStatus(OrdersCookingStatus.NONE)
            .receiptStatus(OrdersReceiptStatus.WAITING)
            .build());

        // Member Entity 로 사용자 장바구니 전체 조회
        List<Cart> cartListByMember = cartRepository.findAllByMember(memberById);
        if (cartListByMember.isEmpty()) {
            throw new CustomException(CART_IS_EMPTY);
        }

        // Cart Entity 로 장바구니_옵션 테이블 조회
        cartListByMember.forEach(cart -> {
            // 장바구니 테이블의 상품 정보들을 주문_상품 테이블에 저장
            ordersProductRepository.save(OrdersProduct.builder()
                .ordersId(savedOrders.getId())
                .product(cart.getProduct())
                .build());

            // 장바구니_옵션 테이블의 옵션 정보들을 주문_상품_옵션 테이블에 저장
            List<CartOption> cartOptionList = cartOptionRepository.findAllByCart(cart);
            cartOptionList.forEach(cartOption ->
                ordersProductOptionRepository.save(
                    OrdersProductOption.builder()
                        .ordersProductId(Long.valueOf(cart.getProduct().getId()))
                        .option(optionRepository.findById(cartOption.getOption().getId())
                            .orElseThrow(() -> new CustomException(OPTION_NOT_EXISTS)))
                        .build())
            );
        });

        // 장바구니 테이블에서 사용자의 모든 장바구니 정보 삭제
        cartRepository.deleteAllByMember(memberById);
    }
}
