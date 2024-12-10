package com.vendoria.product.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateProductRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    private float price;

    @Min(0)
    private Integer stockQuantity;
}
