package com.movieticket.bookingservice.controller;

import com.movieticket.bookingservice.dtos.FetchSeatsResponse;
import com.movieticket.bookingservice.services.ShowSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/showSeats")
@RequiredArgsConstructor
public class ShowSeatController {
    private final ShowSeatService showSeatService;
    @GetMapping("/seatMap/{show_id}")
    public ResponseEntity<List<FetchSeatsResponse>> getAllSeatsForShowWithStatus(@PathVariable Long show_id){
        return ResponseEntity.ok(showSeatService.getAllSeatsForShowWithStatus(show_id));
    }
}
