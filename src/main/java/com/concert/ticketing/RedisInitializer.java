package com.concert.ticketing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.concert.ticketing.domain.concert.seatService.RemainSeatService;

import lombok.RequiredArgsConstructor;

/*
 * TicketService#bookTicket이 Redis 조회가 없다면 제대로 동작하지 않아 임시로 해당 데이터를 넣어준다.
 * 해당 메소드가 캐시 미스 시 DB를 조회하도록 변경되면 필요하지 않다.
 */
@Component
@RequiredArgsConstructor
public class RedisInitializer implements CommandLineRunner {

	private final RemainSeatService remainSeatService;
	private final StringRedisTemplate redisTemplate;

	// Redis에 초기 좌석수를 넣어준다.
	@Override
	public void run(String... args) throws Exception {
		redisTemplate.execute((RedisCallback<Void>)connection -> {
			connection.flushDb();
			return null;
		});
		remainSeatService.getRemainingSeats(1L);
	}
}
