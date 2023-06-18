package com.example2.demo.exception;

public class DuplicatedRoleException extends RuntimeException {
    public DuplicatedRoleException(String message) {
        super(message);
    }
}
