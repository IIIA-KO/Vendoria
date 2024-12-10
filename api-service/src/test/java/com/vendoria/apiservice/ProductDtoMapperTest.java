package com.vendoria.apiservice;

import com.vendoria.product.dto.ProductDto;
import com.vendoria.product.entity.Product;
import com.vendoria.product.mapper.ProductDtoMapper;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDtoMapperTest {
    private final ProductDtoMapper productDtoMapper = new ProductDtoMapper();

    @Test
    void testMapProductToProductDto() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0f);
        product.setStockQuantity(10);

        ProductDto productDto = productDtoMapper.mapProductToProductDto(product);

        assertThat(productDto).isNotNull();
        assertThat(productDto.getId()).isEqualTo(product.getId());
        assertThat(productDto.getName()).isEqualTo(product.getName());
        assertThat(productDto.getDescription()).isEqualTo(product.getDescription());
        assertThat(productDto.getPrice()).isEqualTo(product.getPrice());
        assertThat(productDto.getStockQuantity()).isEqualTo(product.getStockQuantity());
    }

    @Test
    void testMapProductListToProductDtoList() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0f);
        product.setStockQuantity(10);

        List<Product> productList = Collections.singletonList(product);
        List<ProductDto> productDtoList = productDtoMapper.mapProductListToProductDtoList(productList);

        assertThat(productDtoList).isNotEmpty();
        assertThat(productDtoList.get(0).getId()).isEqualTo(product.getId());
    }
}
