package com.movieticket.movieservice.dtos;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreenShowsResponse {
    private Long screenId;
    private String screenName;
    private List<ShowSummaryResponse> shows;
}
