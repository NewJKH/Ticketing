package com.concert.ticketing.common.repository;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisLockRepository {

	private static final String LOCK_PREFIX = "lock:";
	private final StringRedisTemplate redisTemplate;

	//lock 생성
	public Boolean lock(String key, String value, Duration ttl) {
		return redisTemplate.opsForValue()
			.setIfAbsent(LOCK_PREFIX + key, value, ttl);
	}

	//lock 조회
	public String get(String key) {
		return redisTemplate.opsForValue().get(LOCK_PREFIX + key);
	}

	//lock 해제
	public void unlock(String key) {
		redisTemplate.delete(LOCK_PREFIX + key);
	}
}
