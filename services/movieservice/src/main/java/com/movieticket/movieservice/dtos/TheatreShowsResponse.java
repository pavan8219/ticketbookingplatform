package com.movieticket.movieservice.dtos;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TheatreShowsResponse {
    private Long theatreId;
    private String theatreName;
    private String address;
    private String city;
    private List<ScreenShowsResponse> screens;
}
