package com.movieticket.bookingservice.services;

import com.movieticket.bookingservice.dtos.FetchSeatsResponse;

import java.util.List;

public interface ShowSeatService {
    List<FetchSeatsResponse> getAllSeatsForShowWithStatus(Long showId);

}
