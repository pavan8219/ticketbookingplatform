package com.movieticket.bookingservice.dtos;

import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatLockResponse {

    private Long bookingId;
    private List<Long> seatIds;
    private long expiresInSeconds;
}
