package com.example2.demo.exception;

public class DuplicatedLendException extends RuntimeException {
    public DuplicatedLendException(String message) {
        super(message);
    }
}
