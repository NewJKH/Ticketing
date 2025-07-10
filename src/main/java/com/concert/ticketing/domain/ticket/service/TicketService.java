package com.concert.ticketing.domain.ticket.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concert.ticketing.common.exception.CustomErrorCode;
import com.concert.ticketing.common.exception.CustomException;
import com.concert.ticketing.common.template.RedisLockTemplate;
import com.concert.ticketing.domain.concert.entity.Concert;
import com.concert.ticketing.domain.concert.entity.Sector;
import com.concert.ticketing.domain.concert.repository.ConcertRepository;
import com.concert.ticketing.domain.concert.repository.ConcertSectorRepository;
import com.concert.ticketing.domain.member.entity.Member;
import com.concert.ticketing.domain.member.repository.MemberRepository;
import com.concert.ticketing.domain.ticket.dto.response.TicketResponse;
import com.concert.ticketing.domain.ticket.entity.Ticket;
import com.concert.ticketing.domain.ticket.manager.TicketManager;
import com.concert.ticketing.domain.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final TicketRepository ticketRepository;
	private final MemberRepository memberRepository;
	private final ConcertRepository concertRepository;
	private final ConcertSectorRepository sectorRepository;
	private final RedisLockTemplate redisTemplate;
	private final TicketManager ticketManager;

	@Transactional
	public void bookTicket(Sector sector, Long concertId, String email) {

		Member member = memberRepository.findById(email)
			.orElseThrow(
				() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));

		Concert concert = concertRepository.findById(concertId)
			.orElseThrow(
				() -> new CustomException(CustomErrorCode.CONCERT_NOT_FOUND));

		String redisKey = "concert:" + concertId + ":sector:" + sector.name();

		redisTemplate.executeLock(redisKey,()-> {
			Long remain = ticketManager.decrease(redisKey);

			if (remain < 0) {
				ticketManager.increase(redisKey);
				throw new CustomException(CustomErrorCode.TICKET_EMPTY);
			}

			LocalDateTime now = LocalDateTime.now();
			String number = sector.name() + "-" + now;
			Ticket ticket = new Ticket(number, member, sector, concert, now.toLocalDate());
			ticketRepository.save(ticket);
			sectorRepository.decrementRemain(concertId, sector);

		});
	}

	// TODO : concertId 어떤식으로 해야하는지 ?
	@Transactional
	public void deleteMyTickets(String email) {

		Long concertId = 1L;
		List<TicketResponse> myTickets = ticketRepository.getMyTickets(email);
		if (myTickets.isEmpty()) {
			throw new CustomException(CustomErrorCode.TICKET_EMPTY);
		}

		ticketRepository.deleteTickets(email);

		myTickets.forEach(ticketResponse -> {
			Sector sector = ticketResponse.getSector();
			String redisKey = "concert:" + concertId + ":sector:" + sector.name();
			redisTemplate.executeLock(redisKey,()->{
				ticketManager.increase(redisKey);
				sectorRepository.incrementRemain(concertId, sector);
			});
		});
	}

	public List<TicketResponse> getMyTickets(String email) {

		return ticketRepository.getMyTickets(email);
	}
}
