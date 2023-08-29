package com.zerobase.cafebom.orders.service;

import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_ALREADY_CANCELED;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_ALREADY_COOKING_STATUS;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_NOT_COOKING_STATUS;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_NOT_FOUND;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_NOT_RECEIVED_STATUS;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingStatus;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingTime;
import com.zerobase.cafebom.orders.domain.type.OrdersReceiptStatus;
import com.zerobase.cafebom.orders.repository.OrdersRepository;
import com.zerobase.cafebom.orders.service.dto.OrdersCookingTimeModifyDto;
import com.zerobase.cafebom.orders.service.dto.OrdersReceiptModifyDto;
import com.zerobase.cafebom.orders.service.dto.OrdersStatusModifyDto;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    // 주문 상태 변경-minsu-23.08.18
    @Transactional
    public void modifyOrdersStatus(Long ordersId, OrdersStatusModifyDto ordersStatusModifyDto) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_FOUND));

        OrdersCookingStatus newStatus = ordersStatusModifyDto.getNewStatus();

        if (orders.getCookingStatus() == OrdersCookingStatus.COOKING
            && newStatus == OrdersCookingStatus.NONE) {
            throw new CustomException(ORDERS_ALREADY_COOKING_STATUS);
        }

        orders.modifyReceivedTime(newStatus);

        ordersRepository.save(orders);
    }

    // 주문 수락 시간 저장-minsu-23.08.20
    public LocalDateTime getReceivedTime(Long ordersId) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_FOUND));

        if (orders.getCookingStatus() != OrdersCookingStatus.COOKING) {
            throw new CustomException(ORDERS_NOT_COOKING_STATUS);
        }

        return orders.getReceivedTime();
    }

    // 주문 경과 시간 계산-minsu-23.08.21
    public Long getElapsedTime(Long ordersId) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_FOUND));

        if (orders.getCookingStatus() != OrdersCookingStatus.COOKING) {
            throw new CustomException(ORDERS_NOT_COOKING_STATUS);
        }

        LocalDateTime receivedTime = orders.getReceivedTime();
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(receivedTime, currentTime);

        return duration.toMinutes();
    }

    // 주문 수락 또는 거절-minsu-23.08.25
    @Transactional
    public void modifyOrdersReceiptStatus(Long ordersId,
        OrdersReceiptModifyDto ordersReceiptModifyDto) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_FOUND));

        OrdersReceiptStatus newReceiptStatus = ordersReceiptModifyDto.getNewReceiptStatus();

        if (newReceiptStatus == OrdersReceiptStatus.RECEIVED) {
            orders.modifyReceivedTime(OrdersCookingStatus.COOKING);
        }

        if (orders.getReceiptStatus() == OrdersReceiptStatus.CANCELED
            || orders.getReceiptStatus() == OrdersReceiptStatus.REJECTED) {
            throw new CustomException(ORDERS_ALREADY_CANCELED);
        }

        orders.modifyReceiptStatus(newReceiptStatus);

        ordersRepository.save(orders);
    }

    // 주문 취소-minsu-23.08.25
    @Transactional
    public void modifyOrdersCancel(Long ordersId) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_FOUND));

        if (orders.getCookingStatus() == OrdersCookingStatus.COOKING
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

    // 조리 예정 시간 선택-minsu-23.08.28
    @Transactional
    public void modifyOrdersCookingTime(Long ordersId, OrdersCookingTimeModifyDto cookingTimeModifyDto) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_FOUND));

        OrdersCookingTime selectCookingTime = cookingTimeModifyDto.getSelectedCookingTime();

        if (orders.getReceiptStatus() != OrdersReceiptStatus.RECEIVED) {
            throw new CustomException(ORDERS_NOT_RECEIVED_STATUS);
        }

        orders.modifyCookingTime(selectCookingTime);

        ordersRepository.save(orders);
    }
}