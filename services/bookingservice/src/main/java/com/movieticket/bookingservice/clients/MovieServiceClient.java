package com.movieticket.bookingservice.clients;

import com.movieticket.bookingservice.dtos.SeatResponse;
import com.movieticket.bookingservice.dtos.ShowResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "MOVIE-SERVICE")
public interface MovieServiceClient {

    @GetMapping("/api/v1/shows/{id}")
    ShowResponse getShow(@PathVariable Long id);

    @GetMapping("/api/v1/seats/screen/{screen_id}")
    List<SeatResponse> getSeatsByScreen(@PathVariable Long screen_id);

}
