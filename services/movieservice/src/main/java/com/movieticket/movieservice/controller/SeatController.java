package com.movieticket.movieservice.controller;

import com.movieticket.movieservice.dtos.ScreenResponse;
import com.movieticket.movieservice.dtos.SeatCreateRequest;
import com.movieticket.movieservice.dtos.SeatResponse;
import com.movieticket.movieservice.services.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<SeatResponse> create(
            @Valid @RequestBody SeatCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(seatService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponse> getById(@PathVariable Long id) {

        return ResponseEntity.ok(
                seatService.getById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<SeatResponse>> getAll() {

        return ResponseEntity.ok(
                seatService.getAll()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SeatCreateRequest request) {

        return ResponseEntity.ok(
                seatService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/screen/{screed_id}")
    public ResponseEntity<List<SeatResponse>> getByScreenId(@PathVariable Long screed_id){
        return ResponseEntity.ok(seatService.getByScreenId(screed_id));
    }
}
