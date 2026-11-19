package com.movieticket.bookingservice.events;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestEvent {
    private Long bookingId;
    private Long userId;
    private BigDecimal amount;
}
