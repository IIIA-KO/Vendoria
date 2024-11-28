package com.vendoria.order.service;

import com.vendoria.order.entity.Order;
import com.vendoria.order.entity.OrderItem;
import com.vendoria.order.persistence.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order createOrder(List<OrderItem> orderItems) {
        Order order = new Order();

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }

        order.setDate(LocalDate.now());

        order.setItems(orderItems);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(Long id) {
        orderRepository.delete(id);
    }
}
