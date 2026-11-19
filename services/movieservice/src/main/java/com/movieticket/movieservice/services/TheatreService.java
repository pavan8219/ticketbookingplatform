package com.movieticket.movieservice.services;

import com.movieticket.movieservice.dtos.TheatreCreateRequest;
import com.movieticket.movieservice.dtos.TheatreResponse;

import java.util.List;

public interface TheatreService {

    TheatreResponse create(TheatreCreateRequest request);

    TheatreResponse getById(Long id);

    List<TheatreResponse> getAll();

    TheatreResponse update(Long id, TheatreCreateRequest request);

    void delete(Long id);
}
