package com.vendoria.apiservice;

import com.vendoria.product.entity.Product;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {
    @Test
    void testEqualsAndHashCode() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Test Product");

        Product product2 = new Product();
        product2.setId(1L);
        product2.setName("Test Product");

        assertThat(product1).isEqualTo(product2);
        assertThat(product1.hashCode()).isEqualTo(product2.hashCode());
    }

    @Test
    void testToString() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        String expectedString = "Product(id=1, name=Test Product, ...)";
        assertThat(product.toString()).contains("Test Product");
    }
}
