package com.zerobase.cafebom.front.order.service.impl;

import static com.zerobase.cafebom.common.exception.ErrorCode.CART_IS_EMPTY;
import static com.zerobase.cafebom.common.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.OPTION_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_ALREADY_CANCELED;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_ALREADY_COOKING_STATUS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_NOT_ACCESS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_NOT_COOKING_STATUS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_NOT_EXISTS;

import com.zerobase.cafebom.common.config.security.TokenProvider;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.common.type.OrderCookingStatus;
import com.zerobase.cafebom.common.type.OrdersReceiptStatus;
import com.zerobase.cafebom.front.cart.domain.Cart;
import com.zerobase.cafebom.front.cart.domain.CartOption;
import com.zerobase.cafebom.front.cart.domain.CartOptionRepository;
import com.zerobase.cafebom.front.cart.domain.CartRepository;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.member.domain.MemberRepository;
import com.zerobase.cafebom.front.order.domain.Orders;
import com.zerobase.cafebom.front.order.domain.OrdersProduct;
import com.zerobase.cafebom.front.order.domain.OrdersProductOption;
import com.zerobase.cafebom.front.order.domain.OrdersProductOptionRepository;
import com.zerobase.cafebom.front.order.domain.OrdersProductRepository;
import com.zerobase.cafebom.front.order.domain.OrdersRepository;
import com.zerobase.cafebom.front.order.dto.OrdersAddDto;
import com.zerobase.cafebom.front.product.domain.OptionRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 주문 수락 시간 저장-minsu-23.09.12
    public LocalDateTime saveReceivedTime(Long ordersId) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_EXISTS));

        if (orders.getCookingStatus() != OrderCookingStatus.COOKING) {
            throw new CustomException(ORDERS_NOT_COOKING_STATUS);
        }

        return orders.getReceivedTime();
    }

    // 주문 경과 시간 계산-minsu-23.09.19
    public Long findElapsedTime(String token, Long ordersId) {
        Long userId = tokenProvider.getId(token);

        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_EXISTS));

        if (!orders.getMember().getId().equals(userId)) {
            throw new CustomException(ORDERS_NOT_ACCESS);
        }

        if (orders.getCookingStatus() != OrderCookingStatus.COOKING) {
            throw new CustomException(ORDERS_NOT_COOKING_STATUS);
        }

        LocalDateTime receivedTime = orders.getReceivedTime();
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(receivedTime, currentTime);

        return duration.toMinutes();
    }

    // 주문 취소-minsu-23.09.19
    public void modifyOrdersCancel(String token, Long ordersId) {
        Long userId = tokenProvider.getId(token);

        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_EXISTS));

        if (!orders.getMember().getId().equals(userId)) {
            throw new CustomException(ORDERS_NOT_ACCESS);
        }

        if (orders.getCookingStatus() == OrderCookingStatus.COOKING
            || orders.getReceiptStatus() == OrdersReceiptStatus.RECEIVED) {
            throw new CustomException(ORDERS_ALREADY_COOKING_STATUS);
        }

        if (orders.getReceiptStatus() == OrdersReceiptStatus.CANCELED
            || orders.getReceiptStatus() == OrdersReceiptStatus.REJECTED) {
            throw new CustomException(ORDERS_ALREADY_CANCELED);
        }

        orders.modifyReceiptStatus(OrdersReceiptStatus.CANCELED);

        ordersRepository.save(orders);
    }

    // 주문 생성-yesun-23.08.31
    @Transactional
    public void addOrders(String token, OrdersAddDto.Request ordersAddDto) {
        Long userId = tokenProvider.getId(token);
        Member memberById = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        Orders savedOrders = ordersRepository.save(Orders.builder()
            .member(memberById)
            .payment(ordersAddDto.getPayment())
            .cookingStatus(OrderCookingStatus.NONE)
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
        // cartRepository.deleteAllByMember(memberById);
        // 데일리스크럼 때 논의해보고 처리할 예정
    }
}
