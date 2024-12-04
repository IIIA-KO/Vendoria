package com.vendoria.product.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String name;

    private String description;

    @Positive
    private float price;

    @Min(0)
    private Integer stockQuantity;

    public String getFormattedName() {
        return "Product: " + name.toUpperCase();
    }
}
