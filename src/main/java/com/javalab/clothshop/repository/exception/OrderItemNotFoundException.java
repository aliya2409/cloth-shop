package com.javalab.clothshop.repository.exception;

public class OrderItemNotFoundException extends RuntimeException {

    public OrderItemNotFoundException() {
    }

    public OrderItemNotFoundException(String message) {
        super(message);
    }

    public OrderItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
