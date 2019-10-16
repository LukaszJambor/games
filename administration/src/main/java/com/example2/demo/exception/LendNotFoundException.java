package com.example2.demo.exception;

public class LendNotFoundException extends RuntimeException {
    public LendNotFoundException(String message) {
        super(message);
    }
}
