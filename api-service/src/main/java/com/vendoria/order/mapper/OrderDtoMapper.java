package com.vendoria.order.mapper;

import com.vendoria.order.dto.OrderDto;
import com.vendoria.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper {
    public static OrderDto mapOrderToOrderDto(Order order) {
        StringBuilder orderDetails = new StringBuilder("Order ID: ")
                .append(order.getId())
                .append(", Date: ")
                .append(order.getDefaultFormattedDate())
                .append(", Items: ");

        order.getItems().forEach(item -> {
            orderDetails.append("Product ID: ").append(item.getId())
                    .append(", Quantity: ").append(item.getQuantity()).append("; ");
        });

        return OrderDto.builder()
                .id(order.getId())
                .date(order.getDate().atStartOfDay())
                .items(order.getItems().stream()
                        .map(OrderItemDtoMapper::mapOrderItemToOrderItemDto)
                        .toList())
                .orderDetails(orderDetails.toString())
                .build();
    }
}
