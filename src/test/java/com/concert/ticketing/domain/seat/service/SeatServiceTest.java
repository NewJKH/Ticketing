package com.concert.ticketing.domain.seat.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.concert.ticketing.domain.concert.entity.Concert;
import com.concert.ticketing.domain.sector.entity.ConcertSector;
import com.concert.ticketing.domain.sector.type.Sector;
import com.concert.ticketing.domain.concert.repository.ConcertRepository;
import com.concert.ticketing.domain.sector.repository.ConcertSectorRepository;
import com.concert.ticketing.domain.seat.dto.RemainSeatResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class SeatServiceTest {

	@Autowired
	private RemainSeatService remainSeatService;
	@Autowired
	private ConcertSectorRepository concertSectorRepository;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private ConcertRepository concertRepository;

	//given
	@BeforeEach
	void setup() {
		Concert concert = new Concert();
		concert.setTitle("test");
		concert.setDescription("test");
		concert.setDate(LocalDate.now());
		concert.setPlace("place");
		concert.setArtist("artist");

		concert = concertRepository.save(concert);

		ConcertSector concertSector = new ConcertSector();
		concertSector.setConcert(concert);
		concertSector.setName(Sector.A);
		concertSector.setRemain(100);

		concertSectorRepository.save(concertSector);

		redisTemplate.delete("concert:1:sector:A");
	}

	@Test
	void check_cache_when_noCache() {
		//when
		RemainSeatResponseDto result = remainSeatService.getRemainingSeats(1L);

		//then
		assertThat(result.getTotalRemainingSeats()).isEqualTo(100);
		assertThat(result.getSectionSeats().get("A")).isEqualTo(100);

		String cached = redisTemplate.opsForValue().get("concert:1:sector:A");
		assertThat(cached).isEqualTo("100");
	}
}
