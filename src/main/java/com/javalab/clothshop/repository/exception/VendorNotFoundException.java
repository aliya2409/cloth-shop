package com.javalab.clothshop.repository.exception;

public class VendorNotFoundException extends RuntimeException {

    public VendorNotFoundException() {
    }

    public VendorNotFoundException(String message) {
        super(message);
    }

    public VendorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VendorNotFoundException(Throwable cause) {
        super(cause);
    }
}
