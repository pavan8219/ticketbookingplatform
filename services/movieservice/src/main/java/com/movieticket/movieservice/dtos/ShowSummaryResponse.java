package com.movieticket.movieservice.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowSummaryResponse {
    private Long showId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
