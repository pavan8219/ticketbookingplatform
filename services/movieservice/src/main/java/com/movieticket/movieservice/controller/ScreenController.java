package com.movieticket.movieservice.controller;



import com.movieticket.movieservice.dtos.ScreenCreateRequest;
import com.movieticket.movieservice.dtos.ScreenResponse;
import com.movieticket.movieservice.services.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping
    public ResponseEntity<ScreenResponse> create(@Valid @RequestBody ScreenCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(screenService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreenResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                screenService.getById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<ScreenResponse>> getAll() {

        return ResponseEntity.ok(
                screenService.getAll()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreenResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ScreenCreateRequest request) {

        return ResponseEntity.ok(
                screenService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        screenService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
