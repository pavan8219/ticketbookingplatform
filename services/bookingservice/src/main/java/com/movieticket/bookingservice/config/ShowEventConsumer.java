package com.movieticket.bookingservice.config;

import com.movieticket.bookingservice.clients.MovieServiceClient;
import com.movieticket.bookingservice.dtos.SeatResponse;
import com.movieticket.bookingservice.entity.ShowSeat;
import com.movieticket.bookingservice.enums.SeatStatus;
import com.movieticket.bookingservice.events.ShowCreatedEvent;
import com.movieticket.bookingservice.repository.ShowSeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShowEventConsumer {
    private final MovieServiceClient movieServiceClient;
    private final ShowSeatRepository showSeatRepository;
    @KafkaListener(topics = "show-created",groupId = "booking-service")
    @Transactional
    public void consume(ShowCreatedEvent event){
        log.info("Received show created event :{}",event);
        List<SeatResponse> seats=movieServiceClient.getSeatsByScreen(event.screenId());
        List<ShowSeat> showSeats = seats.stream()
                .map(seat -> ShowSeat.builder()
                        .showId(event.showId())
                        .seatId(seat.getId())
                        .status(SeatStatus.AVAILABLE)
                        .build())
                .toList();

        showSeatRepository.saveAll(showSeats);
    }
}
