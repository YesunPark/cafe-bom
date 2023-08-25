package com.zerobase.cafebom.pay.service;

import static com.zerobase.cafebom.exception.ErrorCode.OPTION_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.PRODUCT_NOT_EXISTS;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.member.security.TokenProvider;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.repository.OrdersRepository;
import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import com.zerobase.cafebom.ordersproduct.repository.OrdersProductRepository;
import com.zerobase.cafebom.ordersproductoption.domain.entity.OrdersProductOption;
import com.zerobase.cafebom.ordersproductoption.repository.OrdersProductOptionRepository;
import com.zerobase.cafebom.pay.service.dto.AddOrdersDto;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayService {

    private final MemberRepository memberRepository;
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final OrdersProductOptionRepository ordersProductOptionRepository;
    private final OptionRepository optionRepository;


    private final TokenProvider tokenProvider;

    // 카카오페이 QR 테스트 결제-yesun-23.08.23
    public void payKakaoQR(Model model) {
        model.addAttribute("totalAmount", 20000);
    }

    // 관리자 주문 거절 시 결제 취소-yesun-23.08.24
    public void cancelPayKakaoQR() {

    }

    // 주문 생성-yesun-23.08.25
    public void addOrders(String token, AddOrdersDto addOrdersDto) {
        Long userId = tokenProvider.getId(token);
        Member memberById = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_EXISTS));

        Orders orders = ordersRepository.save(Orders.fromAddOrdersDto(addOrdersDto, memberById));

        addOrdersDto.getProducts()
            .forEach(product -> {
                Product productById = productRepository.findById(product.getProductId())
                    .orElseThrow(() -> new CustomException(PRODUCT_NOT_EXISTS));

                OrdersProduct ordersProduct = ordersProductRepository.save(
                    OrdersProduct.builder()
                        .ordersId(orders.getId())
                        .product(productById)
                        .build());

                List<Integer> optionIds = product.getOptionIds();
                optionIds.forEach(optionId ->
                    ordersProductOptionRepository.save(
                        OrdersProductOption.builder()
                            .ordersProductId(ordersProduct.getId())
                            .option(optionRepository.findById(optionId)
                                .orElseThrow(() -> new CustomException(OPTION_NOT_EXISTS)))
                            .build())
                );
            });
    }
}
