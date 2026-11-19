package com.movieticket.paymentservice.config;

import com.movieticket.paymentservice.events.PaymentSuccessfulEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PaymentSuccessfulEventProducer {
    private final KafkaTemplate<String, PaymentSuccessfulEvent> kafkaTemplate;
    private final String topic_name="payment-success";

    public void publishPaymentSuccessfulEvent(PaymentSuccessfulEvent event){
        kafkaTemplate.send(topic_name,event.bookingId().toString(),event);
        log.info("published payment success event");
    }
}
