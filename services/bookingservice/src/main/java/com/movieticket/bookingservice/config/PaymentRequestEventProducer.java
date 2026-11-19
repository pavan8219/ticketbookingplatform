package com.movieticket.bookingservice.config;

import com.movieticket.bookingservice.events.PaymentRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PaymentRequestEventProducer {
    private final KafkaTemplate<String, PaymentRequestEvent> kafkaTemplate;
    private  final String topic_name="payment-requested";

    public void publishPaymentRequestedEvent(PaymentRequestEvent paymentRequestEvent){
        kafkaTemplate.send(
                topic_name,
                paymentRequestEvent.getBookingId().toString(),
                paymentRequestEvent
        );
        log.info("published payment requested event {}",paymentRequestEvent);
    }

}
