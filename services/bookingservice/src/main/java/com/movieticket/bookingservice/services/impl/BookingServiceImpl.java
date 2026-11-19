package com.movieticket.bookingservice.services.impl;

import com.movieticket.bookingservice.config.PaymentRequestEventProducer;
import com.movieticket.bookingservice.dtos.FetchSeatsResponse;
import com.movieticket.bookingservice.dtos.SeatLockRequest;
import com.movieticket.bookingservice.dtos.SeatLockResponse;
import com.movieticket.bookingservice.entity.Booking;
import com.movieticket.bookingservice.entity.BookingSeat;
import com.movieticket.bookingservice.entity.ShowSeat;
import com.movieticket.bookingservice.enums.BookingStatus;
import com.movieticket.bookingservice.enums.SeatStatus;
import com.movieticket.bookingservice.events.PaymentRequestEvent;
import com.movieticket.bookingservice.repository.BookingRepository;
import com.movieticket.bookingservice.repository.ShowSeatRepository;
import com.movieticket.bookingservice.services.BookingService;
import com.movieticket.bookingservice.services.SeatLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final ShowSeatRepository showSeatRepository;
    private final BookingRepository bookingRepository;
    private final SeatLockService seatLockService;
    private final PaymentRequestEventProducer paymentRequestEventProducer;

    @Override
    @Transactional
    public SeatLockResponse lockSeats(SeatLockRequest request) {

        List<ShowSeat> showSeats = showSeatRepository.findByShowIdAndSeatIdIn(
                        request.getShowId(),
                        request.getSeatIds());

        // 1. Make sure all requested seats exist
        if (showSeats.size() != request.getSeatIds().size()) {
            throw new RuntimeException("One or more seats do not exist");
        }

        // 2. Check DB state
        boolean alreadyBooked = showSeats.stream().anyMatch(seat -> seat.getStatus() == SeatStatus.BOOKED);

        if (alreadyBooked) {
            throw new RuntimeException("One or more seats are already booked");
        }

        List<Long> lockedSeats = new ArrayList<>();

        try {
            // 3. Try to acquire Redis lock for every seat
            for (ShowSeat showSeat : showSeats) {

                boolean locked = seatLockService.tryLock(
                                request.getShowId(),
                                showSeat.getSeatId(),
                                request.getUserId()
                        );

                if (!locked) {
                    throw new RuntimeException("Seat " + showSeat.getSeatId() + " is currently not available");
                }
                lockedSeats.add(showSeat.getSeatId());
            }

            // 4. Create PENDING booking
            Booking booking = Booking.builder()
                    .userId(request.getUserId())
                    .showId(request.getShowId())
                    .status(BookingStatus.PENDING)
                    .totalAmount(BigDecimal.ZERO)
                    .createdAt(Instant.now())
                    .build();

            // 5. Create BookingSeat records
            List<BookingSeat> bookingSeats = showSeats.stream()
                            .map(showSeat -> BookingSeat.builder()
                                            .booking(booking)
                                            .showSeatId(showSeat.getId())
                                            .build())
                                        .toList();

            booking.setBookingSeats(bookingSeats);
            Booking savedBooking = bookingRepository.save(booking);

            paymentRequestEventProducer.publishPaymentRequestedEvent(
                    PaymentRequestEvent.builder()
                            .bookingId(savedBooking.getId())
                            .amount(savedBooking.getTotalAmount())
                            .userId(savedBooking.getUserId())
                            .build()
            );

            return SeatLockResponse.builder()
                    .bookingId(savedBooking.getId())
                    .seatIds(request.getSeatIds())
                    .expiresInSeconds(300)
                    .build();

        } catch (Exception e) {

            // 6. Roll back any Redis locks acquired so far
            for (Long seatId : lockedSeats) {
                seatLockService.unlock(request.getShowId(), seatId);
            }

            throw e;
        }
    }

}
