package com.movieticket.bookingservice.events;

import com.movieticket.bookingservice.enums.PaymentStatus;

import java.math.BigDecimal;

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
