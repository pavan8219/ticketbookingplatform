package com.movieticket.bookingservice.repository;

import com.movieticket.bookingservice.entity.BookingSeat;
import com.movieticket.bookingservice.entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat,Long> {
    void deleteByBookingId(Long id);
    List<BookingSeat> findByBookingId(Long id);
}
