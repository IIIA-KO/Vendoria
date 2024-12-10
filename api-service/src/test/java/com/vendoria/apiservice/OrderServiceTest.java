package com.vendoria.apiservice;

import com.vendoria.order.entity.Order;
import com.vendoria.order.persistence.OrderRepository;
import com.vendoria.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));

        // Act
        List<Order> result = orderService.getAllOrders();

        // Assert
        assertThat(result).isNotEmpty();
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById() {
        // Arrange
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        Order result = orderService.getOrderById(orderId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(orderId);
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(new Order()));

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepository, times(1)).delete(orderId);
    }
}
