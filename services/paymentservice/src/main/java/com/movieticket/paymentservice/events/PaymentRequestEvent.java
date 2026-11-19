package com.movieticket.paymentservice.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestEvent {
    private Long bookingId;
    private Long userId;
    private BigDecimal amount;
}
