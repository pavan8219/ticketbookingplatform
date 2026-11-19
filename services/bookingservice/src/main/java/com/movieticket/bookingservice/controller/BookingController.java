package com.movieticket.bookingservice.controller;

import com.movieticket.bookingservice.dtos.FetchSeatsResponse;
import com.movieticket.bookingservice.dtos.SeatLockRequest;
import com.movieticket.bookingservice.dtos.SeatLockResponse;
import com.movieticket.bookingservice.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/bookTickets")
    public ResponseEntity<SeatLockResponse> lockSeats(@Valid @RequestBody SeatLockRequest request) {
        return ResponseEntity.ok(bookingService.lockSeats(request));
    }


}
