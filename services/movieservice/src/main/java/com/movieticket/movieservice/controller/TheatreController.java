package com.movieticket.movieservice.controller;


import com.movieticket.movieservice.dtos.TheatreCreateRequest;
import com.movieticket.movieservice.dtos.TheatreResponse;
import com.movieticket.movieservice.services.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping
    public ResponseEntity<TheatreResponse> create(@Valid @RequestBody TheatreCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(theatreService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheatreResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                theatreService.getById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<TheatreResponse>> getAll() {

        return ResponseEntity.ok(
                theatreService.getAll()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TheatreResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TheatreCreateRequest request) {

        return ResponseEntity.ok(
                theatreService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        theatreService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
