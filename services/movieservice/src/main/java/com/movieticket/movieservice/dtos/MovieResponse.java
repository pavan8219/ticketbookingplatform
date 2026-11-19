package com.movieticket.movieservice.dtos;


import com.movieticket.movieservice.enums.Genre;
import com.movieticket.movieservice.enums.MovieStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MovieResponse {

    private Long id;
    private String title;
    private String description;
    private Genre genre;
    private Integer durationMinutes;
    private String language;
    private LocalDate releaseDate;
    private MovieStatus status;
}
