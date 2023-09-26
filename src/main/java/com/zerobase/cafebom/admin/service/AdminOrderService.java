package com.zerobase.cafebom.admin.service;

import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_ALREADY_CANCELED;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_COOKING_TIME_ALREADY_SET;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_NOT_RECEIVED_STATUS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_STATUS_ONLY_NEXT;
import static com.zerobase.cafebom.common.type.OrderCookingStatus.COOKING;
import static com.zerobase.cafebom.common.type.OrderCookingStatus.FINISHED;
import static com.zerobase.cafebom.common.type.OrderCookingStatus.PREPARED;

import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.common.type.OrderCookingStatus;
import com.zerobase.cafebom.common.type.OrderCookingTime;
import com.zerobase.cafebom.common.type.OrderReceiptStatus;
import com.zerobase.cafebom.front.order.domain.Order;
import com.zerobase.cafebom.front.order.domain.OrderRepository;
import com.zerobase.cafebom.front.order.dto.OrderCookingTimeModifyDto;
import com.zerobase.cafebom.front.order.dto.OrderReceiptModifyDto;
import com.zerobase.cafebom.front.order.dto.OrderStatusModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orderRepository;

    // 다음 상태 이외엔 주문 상태 변경 불가-minsu-23.09.12
    private OrderCookingStatus modifyNextCookingStatus(OrderCookingStatus currentStatus) {
        switch (currentStatus) {
            case NONE:
                return COOKING;
            case COOKING:
                return PREPARED;
            case PREPARED:
                return FINISHED;
            default:
                return null;
        }
    }

    // 주문 상태 변경-minsu-23.09.19
    public void modifyOrderStatus(Long orderId, OrderStatusModifyDto orderStatusModifyDto) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ORDER_NOT_EXISTS));

        OrderCookingStatus newStatus = orderStatusModifyDto.getNewStatus();
        OrderCookingStatus currentStatus = order.getCookingStatus();
        OrderCookingStatus nextStatus = modifyNextCookingStatus(currentStatus);

        if (newStatus != nextStatus) {
            throw new CustomException(ORDER_STATUS_ONLY_NEXT);
        }

        order.modifyReceivedTime(newStatus);

        orderRepository.save(order);
    }

    // 주문 수락 또는 거절-minsu-23.09.19
    public void modifyOrderReceiptStatus(Long orderId,
        OrderReceiptModifyDto orderReceiptModifyDto) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ORDER_NOT_EXISTS));

        OrderReceiptStatus newReceiptStatus = orderReceiptModifyDto.getNewReceiptStatus();

        if (newReceiptStatus == OrderReceiptStatus.RECEIVED) {
            order.modifyReceivedTime(COOKING);
        }

        if (order.getReceiptStatus() == OrderReceiptStatus.CANCELED
            || order.getReceiptStatus() == OrderReceiptStatus.REJECTED) {
            throw new CustomException(ORDER_ALREADY_CANCELED);
        }

        order.modifyReceiptStatus(newReceiptStatus);

        orderRepository.save(order);
    }

    // 조리 예정 시간 선택-minsu-23.09.19
    public void modifyOrderCookingTime(Long orderId,
        OrderCookingTimeModifyDto cookingTimeModifyDto) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new CustomException(ORDER_NOT_EXISTS));

        OrderCookingTime selectedCookingTime = cookingTimeModifyDto.getSelectedCookingTime();

        if (order.getReceiptStatus() != OrderReceiptStatus.RECEIVED) {
            throw new CustomException(ORDER_NOT_RECEIVED_STATUS);
        }

        if (order.getCookingTime() != OrderCookingTime.NONE
            && selectedCookingTime != order.getCookingTime()) {
            throw new CustomException(ORDER_COOKING_TIME_ALREADY_SET);
        }

        order.modifyCookingTime(selectedCookingTime);

        orderRepository.save(order);
    }
}
