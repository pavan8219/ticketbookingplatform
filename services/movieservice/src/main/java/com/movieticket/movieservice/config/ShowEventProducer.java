package com.movieticket.movieservice.config;

import com.movieticket.movieservice.events.ShowCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShowEventProducer {

    private final KafkaTemplate<String, ShowCreatedEvent> kafkaTemplate;
    private static final String TOPIC = "show-created";
    public void publishShowCreated(ShowCreatedEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.showId().toString(),
                event
        );
    }
}
