package com.movieticket.bookingservice.config;

import com.movieticket.bookingservice.entity.Booking;
import com.movieticket.bookingservice.entity.BookingSeat;
import com.movieticket.bookingservice.entity.ProcessedEvent;
import com.movieticket.bookingservice.enums.BookingStatus;
import com.movieticket.bookingservice.enums.PaymentStatus;
import com.movieticket.bookingservice.enums.SeatStatus;
import com.movieticket.bookingservice.events.PaymentSuccessfulEvent;
import com.movieticket.bookingservice.repository.BookingRepository;
import com.movieticket.bookingservice.repository.BookingSeatRepository;
import com.movieticket.bookingservice.repository.ProcessedEventRepository;
import com.movieticket.bookingservice.repository.ShowSeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.Instant;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class PaymentSuccessEventConsumer {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ProcessedEventRepository processedEventRepository;

    @KafkaListener(topics = "payment-success",groupId = "booking-service")
    @Transactional
    public void consumePaymentSuccessfulEvent(PaymentSuccessfulEvent event){

        if (processedEventRepository.existsByEventId(event.eventId())) {
            return;
        }
        Booking booking=bookingRepository.findById(event.bookingId()).orElseThrow(()-> new RuntimeException("No booking found with id"+event.bookingId()));

        if (event.paymentStatus() == PaymentStatus.SUCCESS) {

            booking.setStatus(BookingStatus.CONFIRMED);

            List<BookingSeat> bookingSeats =
                    bookingSeatRepository.findByBookingId(
                            event.bookingId()
                    );

            for (BookingSeat bookingSeat : bookingSeats) {

                int updated = showSeatRepository.updateStatus(
                        bookingSeat.getShowSeatId(),
                        SeatStatus.AVAILABLE,
                        SeatStatus.BOOKED
                );

                if (updated == 0) {
                    throw new RuntimeException("Seat could not be booked: " + bookingSeat.getShowSeatId());
                }
            }
        } else if (event.paymentStatus()==PaymentStatus.FAILED) {
            booking.setStatus(BookingStatus.FAILED);
            bookingSeatRepository.deleteByBookingId(event.bookingId());
        } else if (event.paymentStatus()==PaymentStatus.PENDING) {
            booking.setStatus(BookingStatus.PENDING);
        }
        processedEventRepository.save(
                ProcessedEvent.builder()
                        .eventId(event.eventId())
                        .processedAt(Instant.now())
                        .build());
        bookingRepository.save(booking);
    }
}
