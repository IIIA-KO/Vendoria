package com.vendoria.order.mapper;

import com.vendoria.order.dto.OrderDto;
import com.vendoria.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper {
    public static OrderDto mapOrderToOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .date(order.getDate().atStartOfDay())
                .items(order.getItems().stream()
                        .map(OrderItemDtoMapper::mapOrderItemToOrderItemDto)
                        .toList())
                .build();
    }
}
