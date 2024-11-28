package com.vendoria.product.errors;

import com.vendoria.common.errors.Error;
import com.vendoria.common.errors.NotFoundError;

public class ProductErrors {
    public static final NotFoundError NOT_FOUND
            = new NotFoundError("Product.NotFound", "Product with specified id was not found");

    public static final Error INSUFFICIENT_STOCK
            = new Error("Product.InsufficientStock", "Insufficient stock for the requested product");
}
