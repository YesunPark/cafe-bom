package com.zerobase.cafebom.orders.service;

import static com.zerobase.cafebom.exception.ErrorCode.COOKING_NOT_CHANGE_NONE;
import static com.zerobase.cafebom.exception.ErrorCode.NOT_COOKING_ORDER;
import static com.zerobase.cafebom.exception.ErrorCode.ORDER_NOT_FOUND;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingStatus;
import com.zerobase.cafebom.orders.repository.OrdersRepository;
import com.zerobase.cafebom.orders.service.dto.OrdersStatusDto;
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
    public void updateOrdersStatus(Long orderId, OrdersStatusDto ordersStatusForm) {
        Orders orders = ordersRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));

        OrdersCookingStatus newStatus = OrdersCookingStatus.valueOf(
            String.valueOf(ordersStatusForm.getNewStatus()));

        if (orders.getCookingStatus() == OrdersCookingStatus.COOKING
            && newStatus == OrdersCookingStatus.NONE) {
          throw new CustomException(COOKING_NOT_CHANGE_NONE);
        }

        if (orders.getCookingStatus() == OrdersCookingStatus.NONE
            && newStatus == OrdersCookingStatus.COOKING) {
          orders.setReceivedTime(LocalDateTime.now());
        }

        orders.setCookingStatus(newStatus);
        ordersRepository.save(orders);
    }

    // 주문 수락 시간 저장-minsu-23.08.20
    public LocalDateTime getReceivedTime(Long ordersId) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));

        if (orders.getCookingStatus() != OrdersCookingStatus.COOKING) {
            throw new CustomException(NOT_COOKING_ORDER);
        }

        return orders.getReceivedTime();
    }

    // 주문 경과 시간 계산-minsu-23.08.21
    public Long calculateElapsedTime(Long ordersId) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));

        if (orders.getCookingStatus() != OrdersCookingStatus.COOKING) {
            throw new CustomException(NOT_COOKING_ORDER);
        }

        LocalDateTime receivedTime = orders.getReceivedTime();
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(receivedTime, currentTime);

        return duration.toMinutes();
    }
}