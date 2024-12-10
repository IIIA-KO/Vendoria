package com.vendoria.apiservice;

import com.vendoria.order.entity.OrderItem;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderItemTest {
    @Test
    void testEqualsAndHashCode() {
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1L);
        orderItem1.setProductId(100L);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(1L);
        orderItem2.setProductId(100L);

        assertThat(orderItem1).isEqualTo(orderItem2);
        assertThat(orderItem1.hashCode()).isEqualTo(orderItem2.hashCode());
    }

    @Test
    void testToString() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProductId(100L);

        String expectedString = "OrderItem(id=1, productId=100, ...)";
        assertThat(orderItem.toString()).contains("productId=100");
    }
}
