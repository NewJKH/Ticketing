package com.concert.ticketing.common.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.concert.ticketing.common.repository.RedisLockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisLockService {

	private static final Duration LOCK_TTL = Duration.ofSeconds(3);
	private final RedisLockRepository redisLockRepository;

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
