package com.movieticket.movieservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Integer capacity;

    @NotNull
    private Long theatreId;
}
