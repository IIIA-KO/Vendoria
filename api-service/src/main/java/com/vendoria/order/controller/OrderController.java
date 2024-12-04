package com.vendoria.order.controller;

import com.vendoria.common.controllers.BaseApiController;
import com.vendoria.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController extends BaseApiController {
    private final OrderService orderService;

    @GetMapping("last")
    public ResponseEntity<?> getLastOrder() {
        return handleResultWithValue(orderService.getLastOrder());
    }
}
