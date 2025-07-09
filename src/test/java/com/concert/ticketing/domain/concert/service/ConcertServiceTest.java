package com.concert.ticketing.domain.concert.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConcertServiceTest {

	@Autowired
	ConcertService concertService;

	@Test
	@DisplayName("CacheMiss_CacheHit_속도측정")
	void cacheMiss() {
		long start = System.currentTimeMillis();
		concertService.getConcert();
		long end = System.currentTimeMillis();
		concertService.getConcert();
		long end2 = System.currentTimeMillis();
		concertService.getConcert();
		long end3 = System.currentTimeMillis();
		concertService.getConcert();
		long end4 = System.currentTimeMillis();
		concertService.getConcert();
		long end5 = System.currentTimeMillis();


		System.out.println("CacheMiss = " + (end-start)+" ms");
		System.out.println("CacheHit_1 = " + (end2-end)+" ms");
		System.out.println("CacheHit_1 = " + (end3-end2)+" ms");
		System.out.println("CacheHit_1 = " + (end4-end3)+" ms");
		System.out.println("CacheHit_1 = " + (end5-end4)+" ms");
	}
}