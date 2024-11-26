package com.vendoria.product.mapper;

import com.vendoria.product.dto.ProductDto;
import com.vendoria.product.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDtoMapper {
    public ProductDto mapProductToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .build();
    }

    public List<ProductDto> mapProductListToProductDtoList(List<Product> productList) {
        return productList.stream()
                .map(this::mapProductToProductDto)
                .toList();
    }
}
