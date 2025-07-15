package com.concert.ticketing.domain.seat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.concert.ticketing.domain.sector.entity.ConcertSector;
import com.concert.ticketing.domain.sector.repository.ConcertSectorRepository;
import com.concert.ticketing.domain.seat.dto.RemainSeatResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemainSeatService {

	private final StringRedisTemplate redisTemplate;
	private final ConcertSectorRepository concertSectorRepository;

	public RemainSeatResponseDto getRemainingSeats(Long concertId) {
		Map<String, Integer> seatMap = new HashMap<>();
		int total = 0;

		List<ConcertSector> sectors = concertSectorRepository.findByConcert_Id(concertId);
		for (ConcertSector sector : sectors) {
			String sectorName = sector.getName().name();
			String redisKey = "concert:" + concertId + ":sector:" + sectorName;

			// Redis 캐시 조회
			String cached = redisTemplate.opsForValue().get(redisKey);
			int remain;

			if (cached != null) {
				remain = Integer.parseInt(cached);
			} else { //캐시 없으면 DB 조회, Redis 캐싱
				remain = sector.getRemain();
				redisTemplate.opsForValue().set(redisKey, String.valueOf(remain), 10, TimeUnit.MINUTES);
			}
			//각 구역별 남은 좌석 수 저장 및 총합 반환
			seatMap.put(sectorName, remain);
			total += remain;
		}

		return new RemainSeatResponseDto(concertId, total, seatMap);
	}
}
