package com.example.hotelmanagement.exceptionHandler;

public class DeleteException extends RuntimeException {
    public DeleteException(String message) {
        super(message);
    }
}
