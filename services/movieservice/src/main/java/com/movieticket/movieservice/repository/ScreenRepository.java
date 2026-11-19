package com.movieticket.movieservice.repository;

import com.movieticket.movieservice.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen,Long> {
    boolean existsByNameAndTheatreId(String name,Long id);
}
