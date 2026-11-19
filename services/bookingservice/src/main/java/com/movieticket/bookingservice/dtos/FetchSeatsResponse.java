package com.movieticket.bookingservice.dtos;

import com.movieticket.bookingservice.enums.SeatStatus;
import com.movieticket.bookingservice.enums.SeatType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FetchSeatsResponse {
    private Long seatId;
    private Long seatNumber;
    private SeatStatus status;
}
