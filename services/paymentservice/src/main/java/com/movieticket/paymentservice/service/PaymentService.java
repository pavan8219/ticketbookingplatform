package com.movieticket.paymentservice.service;

import com.movieticket.paymentservice.config.PaymentSuccessfulEventProducer;
import com.movieticket.paymentservice.entity.Payment;
import com.movieticket.paymentservice.enums.PaymentStatus;
import com.movieticket.paymentservice.events.PaymentRequestEvent;
import com.movieticket.paymentservice.events.PaymentSuccessfulEvent;
import com.movieticket.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentSuccessfulEventProducer paymentSuccessfulEventProducer;

    public Payment processPayment(PaymentRequestEvent event) {

        Payment payment = Payment.builder()
                .bookingId(event.getBookingId())
                .userId(event.getUserId())
                .amount(event.getAmount())
                .status(PaymentStatus.PENDING)
                .transactionId(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        payment = paymentRepository.save(payment);

        // Mimic payment processing
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setUpdatedAt(Instant.now());

        paymentSuccessfulEventProducer.publishPaymentSuccessfulEvent(
                 PaymentSuccessfulEvent.builder()
                         .eventId(UUID.randomUUID().toString())
                        .paymentId(payment.getId())
                        .transactionId(payment.getTransactionId())
                        .amount(payment.getAmount())
                        .bookingId(payment.getBookingId())
                        .userId(payment.getUserId())
                         .paymentStatus(PaymentStatus.SUCCESS)
                        .build()
        );
        return paymentRepository.save(payment);
    }
}
