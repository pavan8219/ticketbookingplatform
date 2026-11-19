package com.movieticket.movieservice.exceptions;

public class ShowNotFoundException extends RuntimeException {

    public ShowNotFoundException(String message) {
        super(message);
    }
}
