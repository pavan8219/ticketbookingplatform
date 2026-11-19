package com.movieticket.bookingservice.dtos;

import com.movieticket.bookingservice.enums.SeatType;
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
