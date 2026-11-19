package com.movieticket.movieservice.dtos;

import com.movieticket.movieservice.enums.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatCreateRequest {

    @NotBlank
    private String seatNumber;

    @NotNull
    private SeatType seatType;

    @NotNull
    private Long screenId;
}
