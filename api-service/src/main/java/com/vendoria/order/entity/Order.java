package com.vendoria.order.entity;

import com.vendoria.common.entities.BaseEntity;
import jakarta.persistence.*;
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
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private LocalDate date;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }

    public String getFormattedDate(String pattern, Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
        return date.format(formatter);
    }

    public String getDefaultFormattedDate() {
        return getFormattedDate("dd-MM-yyyy", Locale.ENGLISH);
    }

    public String getOrderDetails() {
        StringBuilder orderDetails = new StringBuilder("Order Details:\n");
        for (OrderItem item : items) {
            orderDetails.append("Product ID: ").append(item.getProductId())
                    .append(" - Quantity: ").append(item.getQuantity())
                    .append(" - Price: ").append(item.getProductPrice()).append("\n");
        }
        return orderDetails.toString();
    }
}