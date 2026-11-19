package com.movieticket.movieservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreenResponse {

    private Long id;
    private String name;
    private Integer capacity;
    private Long theatreId;
}
