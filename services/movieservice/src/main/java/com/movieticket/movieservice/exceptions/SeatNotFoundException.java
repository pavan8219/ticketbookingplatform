package com.movieticket.movieservice.exceptions;

public class SeatNotFoundException extends RuntimeException{
    public SeatNotFoundException(String message){
        super(message);
    }
}
