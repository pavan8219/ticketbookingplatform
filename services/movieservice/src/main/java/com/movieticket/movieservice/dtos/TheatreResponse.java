package com.movieticket.movieservice.dtos;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TheatreResponse {

    private Long id;
    private String name;
    private String address;
    private String city;
}
