package com.zerobase.cafebom.admin.service;

import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_ALREADY_CANCELED;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_COOKING_TIME_ALREADY_SET;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_NOT_RECEIVED_STATUS;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_STATUS_ONLY_NEXT;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.orders.domain.Orders;
import com.zerobase.cafebom.orders.domain.OrdersRepository;
import com.zerobase.cafebom.orders.dto.OrdersCookingTimeModifyDto;
import com.zerobase.cafebom.orders.dto.OrdersReceiptModifyDto;
import com.zerobase.cafebom.orders.dto.OrdersStatusModifyDto;
import com.zerobase.cafebom.type.OrdersCookingStatus;
import com.zerobase.cafebom.type.OrdersCookingTime;
import com.zerobase.cafebom.type.OrdersReceiptStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrdersService {

    private final OrdersRepository ordersRepository;

    // 다음 상태 이외엔 주문 상태 변경 불가-minsu-23.09.12
    private OrdersCookingStatus modifyNextCookingStatus(OrdersCookingStatus currentStatus) {
        switch (currentStatus) {
            case NONE:
                return OrdersCookingStatus.COOKING;
            case COOKING:
                return OrdersCookingStatus.PREPARED;
            case PREPARED:
                return OrdersCookingStatus.FINISHED;
            default:
                return null;
        }
    }

    // 주문 상태 변경-minsu-23.09.19
    public void modifyOrdersStatus(Long ordersId, OrdersStatusModifyDto ordersStatusModifyDto) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new CustomException(ORDERS_NOT_EXISTS));

        OrdersCookingStatus newStatus = ordersStatusModifyDto.getNewStatus();
        OrdersCookingStatus currentStatus = orders.getCookingStatus();
        OrdersCookingStatus nextStatus = modifyNextCookingStatus(currentStatus);

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
            orders.modifyReceivedTime(OrdersCookingStatus.COOKING);
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

        OrdersCookingTime selectedCookingTime = cookingTimeModifyDto.getSelectedCookingTime();

        if (orders.getReceiptStatus() != OrdersReceiptStatus.RECEIVED) {
            throw new CustomException(ORDERS_NOT_RECEIVED_STATUS);
        }

        if (orders.getCookingTime() != OrdersCookingTime.NONE
            && selectedCookingTime != orders.getCookingTime()) {
            throw new CustomException(ORDERS_COOKING_TIME_ALREADY_SET);
        }

        orders.modifyCookingTime(selectedCookingTime);

        ordersRepository.save(orders);
    }
}
