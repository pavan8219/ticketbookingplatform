package com.movieticket.bookingservice.services;

import com.movieticket.bookingservice.dtos.FetchSeatsResponse;
import com.movieticket.bookingservice.dtos.SeatLockRequest;
import com.movieticket.bookingservice.dtos.SeatLockResponse;

import java.util.List;

public interface BookingService {
    SeatLockResponse lockSeats(SeatLockRequest request);

}
