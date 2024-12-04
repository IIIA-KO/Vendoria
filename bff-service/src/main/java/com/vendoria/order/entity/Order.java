package com.vendoria.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private LocalDate date;

    private List<OrderItem> items = new ArrayList<>();

    public String getOrderDetails() {
        StringBuffer orderDetails = new StringBuffer("OrderDetails:\n");
        //StringBuilder orderDetails = new StringBuilder("OrderDetails:\n");
        for(OrderItem item : items) {
            orderDetails.append(item.getProductId()).append(" - Quantity: ")
                    .append(item.getQuantity()).append("\n");
        }
        return orderDetails.toString();
    }
}
