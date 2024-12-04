package com.vendoria.order.controller;

import com.vendoria.common.handlers.ErrorHandler;
import com.vendoria.order.dto.OrderDto;
import com.vendoria.order.dto.OrderItemDto;
import com.vendoria.order.entity.Order;
import com.vendoria.order.service.OrderService;
import com.vendoria.receipt.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final OrderService orderService;

    @GetMapping("/cart")
    public ModelAndView viewCart() {
        return new ModelAndView("cart");
    }

    @PostMapping("/cart/purchase")
    public ModelAndView purchase(@RequestBody List<OrderItemDto> orderItemDtos, @RequestParam(value = "lang", defaultValue = "en") String lang) {
        if (orderItemDtos == null || orderItemDtos.isEmpty()) {
            return new ModelAndView("redirect:/cart");
        }

        orderService.createOrder(orderItemDtos);

        OrderDto order = orderService.getLastOrder();
        Locale locale = new Locale(lang);
        ReceiptService receiptService = new ReceiptService(locale);
        String receipt = receiptService.generateReceipt(
                order.getItems().getFirst().getProductId().toString(),
                order.getItems().getFirst().getQuantity(),
                order.getItems().getFirst().getProductPrice()
        );

        ModelAndView modelAndView = new ModelAndView("redirect:/receipt");
        modelAndView.addObject("receipt", receipt);
        return modelAndView;
    }
}
