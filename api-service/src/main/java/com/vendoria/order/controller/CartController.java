package com.vendoria.order.controller;

import com.vendoria.common.Result;
import com.vendoria.common.controllers.BaseApiController;
import com.vendoria.common.errors.Error;
import com.vendoria.order.dto.OrderItemDto;
import com.vendoria.order.mapper.OrderItemDtoMapper;
import com.vendoria.order.service.OrderService;
import com.vendoria.product.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Result order(@RequestBody List<OrderItemDto> orderItems) {
        try {
            for (OrderItemDto orderItem : orderItems) {
                var decreaseStockResult = productService.decreaseStock(orderItem.getProductId(), orderItem.getQuantity());

                if (!decreaseStockResult.isSuccess()) {
                    return decreaseStockResult;
                }
            }

            orderService.createOrder(
                    orderItems.stream()
                            .map(OrderItemDtoMapper::mapOrderItemDtoToOrderItem).toList()
            );

            return Result.success();
        } catch (Exception e) {
            log.error("Error while processing order", e);
            return Result.failure(Error.UNEXPECTED);
        }
    }
}
