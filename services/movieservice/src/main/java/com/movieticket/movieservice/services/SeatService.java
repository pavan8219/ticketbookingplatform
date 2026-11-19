package com.movieticket.movieservice.services;

import com.movieticket.movieservice.dtos.SeatCreateRequest;
import com.movieticket.movieservice.dtos.SeatResponse;

import java.util.List;

public interface SeatService {

    SeatResponse create(SeatCreateRequest request);

    SeatResponse getById(Long id);

    List<SeatResponse> getAll();

    SeatResponse update(Long id, SeatCreateRequest request);

    void delete(Long id);

    List<SeatResponse> getByScreenId(Long id);
}
