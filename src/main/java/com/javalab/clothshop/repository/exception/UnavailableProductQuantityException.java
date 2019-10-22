package com.javalab.clothshop.repository.exception;

import com.javalab.clothshop.model.OrderItem;

import java.util.List;

public class UnavailableProductQuantityException extends RuntimeException {

    private List<OrderItem> unavailableProducts;

    public UnavailableProductQuantityException(String message, List<OrderItem> unavailableProducts) {
        super(message);
        this.unavailableProducts = unavailableProducts;
    }

    public UnavailableProductQuantityException() {
    }

    public UnavailableProductQuantityException(String message) {
        super(message);
    }

    public UnavailableProductQuantityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnavailableProductQuantityException(Throwable cause) {
        super(cause);
    }
}
