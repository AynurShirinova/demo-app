package com.example.rest.exceptions;

public class CustomException extends RuntimeException {
    private static final Throwable cause =null ;

    public CustomException(String message) {
        super(message, cause);
    }
}


