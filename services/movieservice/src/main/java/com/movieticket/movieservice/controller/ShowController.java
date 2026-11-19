package com.movieticket.movieservice.controller;

import com.movieticket.movieservice.dtos.ShowCreateRequest;
import com.movieticket.movieservice.dtos.ShowResponse;
import com.movieticket.movieservice.services.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ShowResponse> create(@Valid @RequestBody ShowCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(showService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ShowResponse>> getAll() {
        return ResponseEntity.ok(showService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowResponse> update(@PathVariable Long id, @Valid @RequestBody ShowCreateRequest request) {
        return ResponseEntity.ok(showService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        showService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
