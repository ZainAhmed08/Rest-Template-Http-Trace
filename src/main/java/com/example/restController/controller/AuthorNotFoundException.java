package com.example.restController.controller;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }

    public AuthorNotFoundException(Throwable cause) {
        super(cause);
    }

}
