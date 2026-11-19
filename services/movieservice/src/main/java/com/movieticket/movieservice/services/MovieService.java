package com.movieticket.movieservice.services;



import com.movieticket.movieservice.dtos.MovieCreateRequest;
import com.movieticket.movieservice.dtos.MovieResponse;
import com.movieticket.movieservice.dtos.MovieUpdateRequest;
import com.movieticket.movieservice.dtos.TheatreShowsResponse;

import java.time.LocalDate;
import java.util.List;

public interface MovieService {

    MovieResponse createMovie(MovieCreateRequest request);

    MovieResponse getMovie(Long id);

    List<MovieResponse> getAllMovies();

    MovieResponse updateMovie(Long id, MovieUpdateRequest request);

    void deleteMovie(Long id);

    List<TheatreShowsResponse> getShowsByMovieAndDate(Long movieId, LocalDate date);


}
