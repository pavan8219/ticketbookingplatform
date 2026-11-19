package com.movieticket.movieservice.dtos;


import com.movieticket.movieservice.enums.Genre;
import com.movieticket.movieservice.enums.MovieStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieCreateRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Genre genre;

    @NotNull
    @Positive
    private Integer durationMinutes;

    @NotBlank
    private String language;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private MovieStatus status;
}
