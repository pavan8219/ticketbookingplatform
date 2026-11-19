package com.movieticket.paymentservice.consumers;

import com.movieticket.paymentservice.entity.Payment;
import com.movieticket.paymentservice.events.PaymentRequestEvent;
import com.movieticket.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class PaymentRequestEventConsumer {

    private final PaymentService paymentService;
    @KafkaListener(topics = "payment-requested",groupId = "payment-service")
    public void consumePaymentRequestEvent(PaymentRequestEvent event){
        log.info("consumed payment request event {}",event);
        Payment payment = paymentService.processPayment(event);
    }
}
