package com.concert.ticketing.common.template;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.concert.ticketing.common.exception.CustomErrorCode;
import com.concert.ticketing.common.exception.CustomException;
import com.concert.ticketing.common.service.RedisLockService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisLockTemplate {
	private final RedisLockService redisLockService;

	public void executeLock(String redisKey, Runnable runnable){
		String lockKey = "lock:" + redisKey;
		String lockValue = UUID.randomUUID().toString();
		int maxTry = 5;
		int tryCount = 0;

		try {
			while (!redisLockService.acquireLock(lockKey, lockValue)) {
				if (++tryCount >= maxTry) {
					throw new CustomException(CustomErrorCode.REDIS_WRONG_TYPE);
				}
				Thread.sleep(100);
			}

			runnable.run();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			redisLockService.releaseLock(lockKey, lockValue);
		}
	}
}
