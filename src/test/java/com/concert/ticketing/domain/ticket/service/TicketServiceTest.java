package com.concert.ticketing.domain.ticket.service;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.concert.ticketing.common.exception.CustomException;
import com.concert.ticketing.domain.concert.entity.Sector;
import com.concert.ticketing.domain.member.entity.Member;
import com.concert.ticketing.domain.member.repository.MemberRepository;
import com.concert.ticketing.domain.ticket.repository.TicketRepository;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/insert_for_ticket_service_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/delete_for_ticket_service_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TicketServiceTest {

	@Autowired
	TicketService ticketService;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	StringRedisTemplate redisTemplate;

	@BeforeEach
	void setUp() {
		// 회원 추가
		Member member = new Member("user@email.com", "password", "name");
		memberRepository.save(member);
	}

	@AfterEach
	void cleanUp() {
		ticketRepository.deleteAll();
		memberRepository.deleteAll();
		redisTemplate.execute((RedisCallback<Void>)connection -> {
			connection.flushDb();
			return null;
		});

	}

	@Test
	void 남은_티켓_수보다_많이_구입할_수_없다() {
		// given(+setUp): 일단 하나의 사용자가 동시에 5번의 요청을 보내는 것으로 가정
		final int NUM_REQUEST = 5;
		final int BEFORE_TEST_SECTOR_REMAIN = 2; // sql 파일 참고
		CountDownLatch latch = new CountDownLatch(NUM_REQUEST); // CountDownLatch는 latch 숫자가 특정값이 될 때까지 대기 가능
		redisTemplate.opsForValue()
			.set("concert:1:sector:A", Integer.toString(BEFORE_TEST_SECTOR_REMAIN));

		// when
		ExecutorService es = runNTimesConcurrently(NUM_REQUEST, latch, () -> {
			try {
				ticketService.bookTicket(Sector.A, 1L, "user@email.com");
			} catch (CustomException e) {
				System.out.println(e.getErrorCode());
			}
		});

		try {
			latch.await(); // 모든 트랜잭션이 커밋할 때까지 대기
			// then
			long ticketCount = ticketRepository.count();
			assertThat(ticketCount).isLessThanOrEqualTo(BEFORE_TEST_SECTOR_REMAIN);
		} catch (InterruptedException e) {
			throw new RuntimeException();
		} finally {
			es.shutdown();
		}
	}

	private ExecutorService runNTimesConcurrently(int n, CountDownLatch latch, Runnable job) {
		ExecutorService es = Executors.newFixedThreadPool(n);
		CyclicBarrier barrier = new CyclicBarrier(n); // CyclicBarrier는 일정 수의 스레드가 쌓일 때까지 스레드 대기

		for (int i = 0; i < n; i++) {
			es.submit(() -> {
				try {
					barrier.await();
					System.out.println(Thread.currentThread() + " started at :" + System.currentTimeMillis());
					job.run();
				} catch (InterruptedException | BrokenBarrierException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown();
				}
			});
		}

		return es;
	}
}