package com.movieticket.user_service.repository;

import com.movieticket.user_service.entity.RefreshToken;
import com.movieticket.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
    List<RefreshToken> findByUser(User user);
}
