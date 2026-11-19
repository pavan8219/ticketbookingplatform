package com.movieticket.movieservice.exceptions;

public class SeatAlreadyExistsException extends RuntimeException{
    public SeatAlreadyExistsException(String message){
        super(message);
    }
}
