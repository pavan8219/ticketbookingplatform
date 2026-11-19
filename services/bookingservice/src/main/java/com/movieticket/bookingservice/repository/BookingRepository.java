package com.movieticket.bookingservice.repository;

import com.movieticket.bookingservice.entity.Booking;
import com.movieticket.bookingservice.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
