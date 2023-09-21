package com.zerobase.cafebom.admin.service;

import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_ALREADY_CANCELED;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_COOKING_TIME_ALREADY_SET;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_NOT_RECEIVED_STATUS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_STATUS_ONLY_NEXT;
import static com.zerobase.cafebom.common.type.OrderCookingStatus.COOKING;
import static com.zerobase.cafebom.common.type.OrderCookingStatus.FINISHED;
import static com.zerobase.cafebom.common.type.OrderCookingStatus.PREPARED;

import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.common.type.OrderCookingStatus;
import com.zerobase.cafebom.common.type.OrderCookingTime;
import com.zerobase.cafebom.common.type.OrdersReceiptStatus;
import com.zerobase.cafebom.front.order.domain.Orders;
import com.zerobase.cafebom.front.order.domain.OrdersRepository;
import com.zerobase.cafebom.front.order.dto.OrdersCookingTimeModifyDto;
import com.zerobase.cafebom.front.order.dto.OrdersReceiptModifyDto;
import com.zerobase.cafebom.front.order.dto.OrdersStatusModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrdersService {

    private final OrdersRepository ordersRepository;

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
    public void modifyOrdersStatus(Long ordersId, OrdersStatusModifyDto ordersStatusModifyDto) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_EXISTS));

        OrderCookingStatus newStatus = ordersStatusModifyDto.getNewStatus();
        OrderCookingStatus currentStatus = orders.getCookingStatus();
        OrderCookingStatus nextStatus = modifyNextCookingStatus(currentStatus);

        if (newStatus != nextStatus) {
            throw new CustomException(ORDERS_STATUS_ONLY_NEXT);
        }

        orders.modifyReceivedTime(newStatus);

        ordersRepository.save(orders);
    }

    // 주문 수락 또는 거절-minsu-23.09.19
    public void modifyOrdersReceiptStatus(Long ordersId,
        OrdersReceiptModifyDto ordersReceiptModifyDto) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_EXISTS));

        OrdersReceiptStatus newReceiptStatus = ordersReceiptModifyDto.getNewReceiptStatus();

        if (newReceiptStatus == OrdersReceiptStatus.RECEIVED) {
            orders.modifyReceivedTime(COOKING);
        }

        if (orders.getReceiptStatus() == OrdersReceiptStatus.CANCELED
            || orders.getReceiptStatus() == OrdersReceiptStatus.REJECTED) {
            throw new CustomException(ORDERS_ALREADY_CANCELED);
        }

        orders.modifyReceiptStatus(newReceiptStatus);

        ordersRepository.save(orders);
    }

    // 조리 예정 시간 선택-minsu-23.09.19
    public void modifyOrdersCookingTime(Long ordersId,
        OrdersCookingTimeModifyDto cookingTimeModifyDto) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_EXISTS));

        OrderCookingTime selectedCookingTime = cookingTimeModifyDto.getSelectedCookingTime();

        if (orders.getReceiptStatus() != OrdersReceiptStatus.RECEIVED) {
            throw new CustomException(ORDERS_NOT_RECEIVED_STATUS);
        }

        if (orders.getCookingTime() != OrderCookingTime.NONE
            && selectedCookingTime != orders.getCookingTime()) {
            throw new CustomException(ORDERS_COOKING_TIME_ALREADY_SET);
        }

        orders.modifyCookingTime(selectedCookingTime);

        ordersRepository.save(orders);
    }
}
