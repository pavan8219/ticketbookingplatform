package com.movieticket.movieservice.repository;

import com.movieticket.movieservice.dtos.SeatResponse;
import com.movieticket.movieservice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    boolean existsByScreenIdAndSeatNumber(Long screenId, String seatNumber);
    boolean existsByScreenId(Long id);
    List<Seat> findByScreenId(Long id);
}
