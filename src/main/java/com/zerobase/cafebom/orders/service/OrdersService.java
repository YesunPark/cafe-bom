package com.zerobase.cafebom.orders.service;

import static com.zerobase.cafebom.exception.ErrorCode.OPTION_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.PRODUCT_NOT_EXISTS;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.repository.OrdersRepository;
import com.zerobase.cafebom.orders.service.dto.OrdersAddDto;
import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import com.zerobase.cafebom.ordersproduct.repository.OrdersProductRepository;
import com.zerobase.cafebom.ordersproductoption.domain.entity.OrdersProductOption;
import com.zerobase.cafebom.ordersproductoption.repository.OrdersProductOptionRepository;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
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
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final OrdersProductOptionRepository ordersProductOptionRepository;
    private final OptionRepository optionRepository;

    private final TokenProvider tokenProvider;

    // 주문 생성-yesun-23.08.29
    @Transactional
    public void addOrders(String token, OrdersAddDto.Request ordersAddDto) {
        Long userId = tokenProvider.getId(token);
        Member memberById = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_EXISTS));

        // Orders 레파지토리 저장
        Orders orders = ordersRepository.save(Orders.fromAddOrdersDto(ordersAddDto, memberById));

        ordersAddDto.getProducts()
            .forEach(product -> {
                Product productById = productRepository.findById(product.getProductId())
                    .orElseThrow(() -> new CustomException(PRODUCT_NOT_EXISTS));

                // OrdersProduct 레파지토리 저장
                OrdersProduct ordersProduct = ordersProductRepository.save(
                    OrdersProduct.builder()
                        .ordersId(orders.getId())
                        .product(productById)
                        .build());

                List<Integer> optionIds = product.getOptionIds();
                optionIds.forEach(optionId ->
                    // OrdersProductOption 레파지토리 저장
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
