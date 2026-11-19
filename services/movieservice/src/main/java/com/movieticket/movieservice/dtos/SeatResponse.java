package com.movieticket.movieservice.dtos;

import com.movieticket.movieservice.enums.SeatType;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {

    private Long id;
    private String seatNumber;
    private SeatType seatType;
    private Long screenId;
}
