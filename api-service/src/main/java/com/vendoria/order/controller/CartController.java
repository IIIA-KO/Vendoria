package com.vendoria.order.controller;

import com.vendoria.common.Result;
import com.vendoria.common.controllers.BaseApiController;
import com.vendoria.common.errors.Error;
import com.vendoria.order.dto.OrderItemDto;
import com.vendoria.order.mapper.OrderItemDtoMapper;
import com.vendoria.order.service.OrderService;
import com.vendoria.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController extends BaseApiController {
    private final ProductService productService;
    private final OrderService orderService;

    @PostMapping("order")
    public ResponseEntity<?> order(@RequestBody List<OrderItemDto> orderItems) {

        for (OrderItemDto orderItem : orderItems) {
            var decreaseStockResult = productService.decreaseStock(orderItem.getProductId(), orderItem.getQuantity());

            if (!decreaseStockResult.isSuccess()) {
                return null;
            }
        }

        var result = orderService.createOrder(
                orderItems.stream()
                        .map(OrderItemDtoMapper::mapOrderItemDtoToOrderItem).toList()
        );

        return handleResult(result);
    }
}
