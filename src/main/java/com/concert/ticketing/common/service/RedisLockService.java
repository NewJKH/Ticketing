package com.concert.ticketing.common.service;

import com.concert.ticketing.common.repository.RedisLockRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisLockService {

  private final RedisLockRepository redisLockRepository;
  private static final Duration LOCK_TTL = Duration.ofSeconds(3);

  //Lock 획득
  public boolean acquireLock(String key, String value) {
    return Boolean.TRUE.equals(redisLockRepository.lock(key, value, LOCK_TTL));
  }

  //Lock 해제
  public void releaseLock(String key, String value) {
    String storedValue = redisLockRepository.get(key);
    if (value.equals(storedValue)) {
      redisLockRepository.unlock(key);
    }
  }
}
