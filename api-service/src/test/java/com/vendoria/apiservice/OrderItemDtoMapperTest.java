package com.vendoria.apiservice;

import com.vendoria.order.dto.OrderItemDto;
import com.vendoria.order.entity.Order;
import com.vendoria.order.entity.OrderItem;
import com.vendoria.order.mapper.OrderItemDtoMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderItemDtoMapperTest {
    @Test
    void testMapOrderItemToOrderItemDto() {
        Order order = new Order();
        order.setId(1L);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProductId(100L);
        orderItem.setQuantity(2);
        orderItem.setProductPrice(50.0);
        orderItem.setOrder(order);

        OrderItemDto orderItemDto = OrderItemDtoMapper.mapOrderItemToOrderItemDto(orderItem);

        assertThat(orderItemDto).isNotNull();
        assertThat(orderItemDto.getId()).isEqualTo(orderItem.getId());
        assertThat(orderItemDto.getProductId()).isEqualTo(orderItem.getProductId());
        assertThat(orderItemDto.getQuantity()).isEqualTo(orderItem.getQuantity());
        assertThat(orderItemDto.getProductPrice()).isEqualTo(orderItem.getProductPrice());
    }

    @Test
    void testMapOrderItemDtoToOrderItem() {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(100L);
        orderItemDto.setQuantity(2);
        orderItemDto.setProductPrice(50.0);

        OrderItem orderItem = OrderItemDtoMapper.mapOrderItemDtoToOrderItem(orderItemDto);

        assertThat(orderItem).isNotNull();
        assertThat(orderItem.getProductId()).isEqualTo(orderItemDto.getProductId());
        assertThat(orderItem.getQuantity()).isEqualTo(orderItemDto.getQuantity());
        assertThat(orderItem.getProductPrice()).isEqualTo(orderItemDto.getProductPrice());
    }
}
