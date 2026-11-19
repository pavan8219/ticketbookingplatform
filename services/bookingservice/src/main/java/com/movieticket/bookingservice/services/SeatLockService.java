package com.movieticket.bookingservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatLockService {

    private final StringRedisTemplate redisTemplate;
    private static final long LOCK_DURATION_SECONDS = 300;
    public boolean tryLock(Long showId, Long seatId, Long userId) {

        String key = buildKey(showId, seatId);
        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(
                        key,
                        userId.toString(),
                        Duration.ofSeconds(LOCK_DURATION_SECONDS)
                );
        return Boolean.TRUE.equals(acquired);
    }
    public void unlock(Long showId, Long seatId) {
        String key = buildKey(showId, seatId);
        redisTemplate.delete(key);
    }

    public List<String> getLockedSeatValues(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    public String buildKey(Long showId, Long seatId) {
        return "show-seat:" + showId + ":" + seatId;
    }
}
