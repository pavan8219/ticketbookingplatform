package com.movieticket.movieservice.repository;

import com.movieticket.movieservice.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByScreenId(Long screenId);

    List<Show> findByMovieId(Long movieId);

    boolean existsByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(
            Long screenId,
            LocalDateTime endTime,
            LocalDateTime startTime
    );


        @Query("""
        SELECT COUNT(s) > 0
        FROM Show s
        WHERE s.screen.id = :screenId
          AND s.startTime < :endTime
          AND s.endTime > :startTime
    """)
        boolean existsOverlappingShow(
                @Param("screenId") Long screenId,
                @Param("startTime") LocalDateTime startTime,
                @Param("endTime") LocalDateTime endTime
        );

        @Query("""
        SELECT COUNT(s) > 0
        FROM Show s
        WHERE s.screen.id = :screenId
          AND s.id <> :showId
          AND s.startTime < :endTime
          AND s.endTime > :startTime
    """)
        boolean existsOverlappingShowExcludingId(
                @Param("screenId") Long screenId,
                @Param("showId") Long showId,
                @Param("startTime") LocalDateTime startTime,
                @Param("endTime") LocalDateTime endTime
        );

    @Query("""
    SELECT s
    FROM Show s
    JOIN FETCH s.screen sc
    JOIN FETCH sc.theatre t
    WHERE s.movie.id = :movieId
      AND s.startTime >= :startOfDay
      AND s.startTime < :nextDay
    """)
    List<Show> findShowsByMovieAndDate(
            @Param("movieId") Long movieId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("nextDay") LocalDateTime nextDay
    );

}
