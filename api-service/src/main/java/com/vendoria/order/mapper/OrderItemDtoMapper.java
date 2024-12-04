package com.vendoria.order.mapper;

import com.vendoria.order.dto.OrderItemDto;
import com.vendoria.order.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemDtoMapper {
    public static OrderItemDto mapOrderItemToOrderItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .productId(orderItem.getProductId())
                .build();
    }

    public static OrderItem mapOrderItemDtoToOrderItem(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .productId(orderItemDto.getProductId())
                .quantity(orderItemDto.getQuantity())
                .productPrice(orderItemDto.getProductPrice())
                .build();
    }
}
