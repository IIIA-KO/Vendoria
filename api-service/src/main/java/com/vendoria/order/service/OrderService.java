package com.vendoria.order.service;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.errors.Error;
import com.vendoria.order.dto.OrderDto;
import com.vendoria.order.dto.OrderItemDto;
import com.vendoria.order.entity.Order;
import com.vendoria.order.entity.OrderItem;
import com.vendoria.order.errors.OrderErrors;
import com.vendoria.order.persistence.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Result createOrder(List<OrderItem> orderItems) {
        try {
            Order order = new Order();

            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(order);
            }

            order.setDate(LocalDate.now());

            order.setItems(orderItems);
            var result = orderRepository.save(order);
        } catch (Exception e) {
            log.error("Error during order processing", e);
            return Result.failure(Error.UNEXPECTED);
        }
        return Result.success();
    }

    public ResultWithValue<OrderDto> getLastOrder() {
        Order order = orderRepository.getLast().orElse(null);

        if (order == null) {
            return ResultWithValue.failureWithResult(OrderErrors.NOT_FOUND);
        }

        List<OrderItemDto> orderItemDtos = order.getItems().stream()
                .map(item -> OrderItemDto.builder()
                        .id(item.getId())
                        .orderId(item.getOrder().getId())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .productPrice(item.getProductPrice())
                        .build())
                .collect(Collectors.toList());

        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .date(order.getDate().atStartOfDay())
                .items(orderItemDtos)
                .orderDetails(order.getOrderDetails())
                .build();

        return ResultWithValue.successWithValue(orderDto);
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
