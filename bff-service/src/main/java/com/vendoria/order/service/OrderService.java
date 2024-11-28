package com.vendoria.order.service;

import com.vendoria.bff.feign.client.IVendoriaApiClient;
import com.vendoria.common.Result;
import com.vendoria.order.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final IVendoriaApiClient vendoriaApiClient;

    public Result createOrder(List<OrderItemDto> orderItems) {
        return vendoriaApiClient.createOrder(orderItems);
    }
}
