package com.zerobase.cafebom.front.order.dto;

import com.zerobase.cafebom.front.order.domain.Order;
import com.zerobase.cafebom.common.type.OrderReceiptStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class OrderHisDto {

    public OrderHisDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getCreatedDate();
        this.orderReceiptStatus = order.getReceiptStatus();
    }

    private Long orderId;

    private LocalDateTime orderDate;

    private OrderReceiptStatus orderReceiptStatus;

    private List<OrderProductDto> orderedProductsList;

    public void addOrderProductDto(OrderProductDto orderProductDto) {
        orderedProductsList.add(orderProductDto);
    }
}
