package com.movieticket.movieservice.exceptions;

public class TheatreNotFoundException extends RuntimeException{
    public TheatreNotFoundException(String message){
        super(message);
    }
}
