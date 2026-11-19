package com.movieticket.bookingservice.repository;

import com.movieticket.bookingservice.entity.ShowSeat;
import com.movieticket.bookingservice.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {

    boolean existsByShowIdAndSeatId(Long showId, Long seatId);
    List<ShowSeat> findByShowId(Long showId);
    List<ShowSeat> findByShowIdAndSeatIdIn(Long showId, List<Long> seatIds);

    @Modifying
    @Query("""
    UPDATE ShowSeat s
    SET s.status = :newStatus
    WHERE s.id = :showSeatId
      AND s.status = :currentStatus
""")
    int updateStatus(
            @Param("showSeatId") Long showSeatId,
            @Param("currentStatus") SeatStatus currentStatus,
            @Param("newStatus") SeatStatus newStatus
    );
}
