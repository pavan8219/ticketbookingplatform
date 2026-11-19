package com.movieticket.movieservice.services;

import com.movieticket.movieservice.dtos.ScreenCreateRequest;
import com.movieticket.movieservice.dtos.ScreenResponse;

import java.util.List;

public interface ScreenService {

    ScreenResponse create(ScreenCreateRequest request);

    ScreenResponse getById(Long id);

    List<ScreenResponse> getAll();

    ScreenResponse update(Long id, ScreenCreateRequest request);

    void delete(Long id);
}
