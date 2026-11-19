package com.movieticket.paymentservice.events;

import com.movieticket.paymentservice.enums.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentSuccessfulEvent(
        String eventId,
        Long paymentId,
        Long bookingId,
        Long userId,
        BigDecimal amount,
        String transactionId,
        PaymentStatus paymentStatus
) {
}
