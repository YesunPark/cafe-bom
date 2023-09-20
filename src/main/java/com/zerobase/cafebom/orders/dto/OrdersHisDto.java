package com.zerobase.cafebom.orders.dto;

import com.zerobase.cafebom.orders.domain.Orders;
import com.zerobase.cafebom.type.OrdersReceiptStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class OrdersHisDto {

    public OrdersHisDto(Orders order) {
        this.orderId = order.getId();
        this.orderDate = order.getCreatedDate();
        this.ordersReceiptStatus = order.getReceiptStatus();
    }

    private Long orderId;

    private LocalDateTime orderDate;

    private OrdersReceiptStatus ordersReceiptStatus;

    private List<OrdersProductDto> orderedProductsList;

    public void addOrderProductDto(OrdersProductDto ordersProductDto) {
        orderedProductsList.add(ordersProductDto);
    }
}
