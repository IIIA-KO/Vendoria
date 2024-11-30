package com.vendoria.order.controller;

import com.vendoria.common.handlers.ErrorHandler;
import com.vendoria.order.dto.OrderItemDto;
import com.vendoria.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final OrderService orderService;

    @GetMapping("/cart")
    public ModelAndView viewCart() {
        return new ModelAndView("cart");
    }

    @PostMapping("/cart/purchase")
    public ModelAndView purchase(@RequestBody List<OrderItemDto> orderItemDtos) {
        if (orderItemDtos == null || orderItemDtos.isEmpty()) {
            return new ModelAndView("redirect:/cart");
        }

        var result = orderService.createOrder(orderItemDtos);

        return ErrorHandler.handleResult(result, "cart", "cart");
    }
}
