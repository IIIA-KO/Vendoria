package com.vendoria.apiservice;

import com.vendoria.order.dto.OrderDto;
import com.vendoria.order.entity.Order;
import com.vendoria.order.entity.OrderItem;
import com.vendoria.order.mapper.OrderDtoMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderDtoMapperTest {
    @Test
    void testMapOrderToOrderDto() {
        Order order = new Order();
        order.setId(1L);
        order.setDate(LocalDate.now());
        OrderItem item = new OrderItem();
        item.setId(1L);
        item.setQuantity(2);
        item.setProductPrice(50.0);
        order.addItem(item);

        OrderDto orderDto = OrderDtoMapper.mapOrderToOrderDto(order);

        assertThat(orderDto).isNotNull();
        assertThat(orderDto.getId()).isEqualTo(order.getId());
        assertThat(orderDto.getItems()).isNotNull();
        assertThat(orderDto.getItems().get(0).getProductId()).isEqualTo(item.getProductId());
    }
}
