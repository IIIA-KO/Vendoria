package com.vendoria.product.filter;

import com.vendoria.product.entity.Product;

@FunctionalInterface
public interface ProductFilter {
    boolean test(Product product);
}
