package com.concert.ticketing.domain.ticket.manager;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.concert.ticketing.common.exception.CustomErrorCode;
import com.concert.ticketing.common.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TicketManager {
	private final StringRedisTemplate stringRedisTemplate;

	public Long decrease(String redisKey){
		Long remain = stringRedisTemplate.opsForValue().decrement(redisKey);
		if ( remain == null ){
			throw new CustomException(CustomErrorCode.REDIS_WRONG_TYPE);
		}
		return remain;
	}

	public void increase(String redisKey){
		stringRedisTemplate.opsForValue().increment(redisKey);
	}
}
