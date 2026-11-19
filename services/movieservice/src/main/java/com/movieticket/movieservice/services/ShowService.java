package com.movieticket.movieservice.services;

import com.movieticket.movieservice.dtos.ShowCreateRequest;
import com.movieticket.movieservice.dtos.ShowResponse;

import java.util.List;

public interface ShowService {

    ShowResponse create(ShowCreateRequest request);
    ShowResponse getById(Long id);
    List<ShowResponse> getAll();
    ShowResponse update(Long id, ShowCreateRequest request);
    void delete(Long id);
}
