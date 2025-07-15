package com.concert.ticketing.domain.ticket.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concert.ticketing.common.aop.RedisLock;
import com.concert.ticketing.common.exception.CustomErrorCode;
import com.concert.ticketing.common.exception.CustomException;
import com.concert.ticketing.domain.concert.entity.Concert;
import com.concert.ticketing.domain.concert.entity.ConcertSector;
import com.concert.ticketing.domain.concert.entity.Sector;
import com.concert.ticketing.domain.concert.repository.ConcertRepository;
import com.concert.ticketing.domain.concert.repository.ConcertSectorRepository;
import com.concert.ticketing.domain.member.entity.Member;
import com.concert.ticketing.domain.member.repository.MemberRepository;
import com.concert.ticketing.domain.ticket.dto.response.TicketResponse;
import com.concert.ticketing.domain.ticket.entity.Ticket;
import com.concert.ticketing.domain.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final TicketRepository ticketRepository;
	private final MemberRepository memberRepository;
	private final ConcertRepository concertRepository;
	private final StringRedisTemplate redisTemplate;
	private final ConcertSectorRepository sectorRepository;
	private final ObjectProvider<TicketService> self;

	@RedisLock(key = "'lock:concert:' + #concertId + ':sector:' + #sector.name()")
	@Transactional
	public void bookTicket(Sector sector, Long concertId, String email) {

		Member member = memberRepository.findById(email)
			.orElseThrow(
				() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));

		Concert concert = concertRepository.findById(concertId)
			.orElseThrow(
				() -> new CustomException(CustomErrorCode.CONCERT_NOT_FOUND));

		String redisKey = "concert:" + concertId + ":sector:" + sector.name();

		String cached = redisTemplate.opsForValue().get(redisKey);
		Long remain;

		if (cached == null) {
			// 캐시 존재 X
			ConcertSector sectorConcert = sectorRepository.findByConcert_IdAndName(concertId, sector);
			if (sectorConcert.getRemain() <= 0) {
				throw new CustomException(CustomErrorCode.TICKET_EMPTY);
			}
			remain = (long)sectorConcert.getRemain() - 1;
			redisTemplate.opsForValue().set(redisKey, String.valueOf(remain), 10, TimeUnit.MINUTES);
		} else {
			// 캐시 존재
			remain = redisTemplate.opsForValue().decrement(redisKey);
			if (remain < 0) {
				redisTemplate.opsForValue().increment(redisKey);
				throw new CustomException(CustomErrorCode.TICKET_EMPTY);
			}
		}

		LocalDateTime now = LocalDateTime.now();
		String number = sector.name() + "-" + now;
		Ticket ticket = new Ticket(number, member, sector, concert, now.toLocalDate());
		ticketRepository.save(ticket);
		sectorRepository.decrementRemain(concertId, sector);
	}

	@Transactional
	public void deleteMyTickets(String email) {

		Long concertId = 1L;
		List<TicketResponse> myTickets = ticketRepository.getMyTickets(email);
		if (myTickets.isEmpty()) {

			throw new CustomException(CustomErrorCode.TICKET_EMPTY);
		}

		ticketRepository.deleteTickets(email);

		for (TicketResponse ticketResponse : myTickets) {
			Sector sector = ticketResponse.getSector();
			self.getObject().deleteTicket(concertId, sector);
		}
	}

	public List<TicketResponse> getMyTickets(String email) {

		return ticketRepository.getMyTickets(email);
	}

	@RedisLock(key = "'lock:concert:' + #concertId + ':sector:' + #sector.name()")
	public void deleteTicket(Long concertId, Sector sector) {
		String redisKey = "concert:" + concertId + ":sector:" + sector.name();
		redisTemplate.opsForValue().increment(redisKey);
		sectorRepository.incrementRemain(concertId, sector);
	}
}
