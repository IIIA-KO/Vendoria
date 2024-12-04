package com.vendoria.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderItemDto {
    private Long productId;
    private String productName;
    private Double productPrice;
    private Integer quantity;
}
