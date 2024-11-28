package com.vendoria.order.errors;

import com.vendoria.common.errors.NotFoundError;

public class OrderErrors {
    public static final NotFoundError NOT_FOUND
            = new NotFoundError("Order.NotFound", "Order with specified id was not found");
}
