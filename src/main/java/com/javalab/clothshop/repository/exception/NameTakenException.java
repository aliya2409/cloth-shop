package com.javalab.clothshop.repository.exception;

public class NameTakenException extends RuntimeException {

    public NameTakenException() {
    }

    public NameTakenException(String message) {
        super(message);
    }

    public NameTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameTakenException(Throwable cause) {
        super(cause);
    }
}
