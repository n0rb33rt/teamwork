package com.norbert.frontend.exception;

public class ApiServiceException extends RuntimeException{
    public ApiServiceException(String message) {
        super(message);
    }
}
