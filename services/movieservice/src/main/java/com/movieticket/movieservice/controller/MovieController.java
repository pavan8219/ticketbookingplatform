package com.movieticket.movieservice.controller;


import com.movieticket.movieservice.dtos.MovieCreateRequest;
import com.movieticket.movieservice.dtos.MovieResponse;
import com.movieticket.movieservice.dtos.MovieUpdateRequest;
import com.movieticket.movieservice.dtos.TheatreShowsResponse;
import com.movieticket.movieservice.services.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(
            @Valid @RequestBody MovieCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movieService.createMovie(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovie(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                movieService.getMovie(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {

        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieUpdateRequest request) {

        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {

        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{movieId}/shows")
    public ResponseEntity<List<TheatreShowsResponse>> getShowsByMovie(@PathVariable Long movieId,@RequestParam LocalDate date) {
        return ResponseEntity.ok(movieService.getShowsByMovieAndDate(movieId, date));
    }
}
