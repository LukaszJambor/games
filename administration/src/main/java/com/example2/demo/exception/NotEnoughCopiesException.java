package com.example2.demo.exception;

public class NotEnoughCopiesException extends RuntimeException {
    public NotEnoughCopiesException(String message) {
        super(message);
    }
}
