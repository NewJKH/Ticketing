package com.concert.ticketing.domain.ticket.service;

import com.concert.ticketing.common.exception.CustomErrorCode;
import com.concert.ticketing.domain.concert.entity.Concert;
import com.concert.ticketing.domain.concert.entity.Sector;
import com.concert.ticketing.domain.concert.repository.ConcertRepository;
import com.concert.ticketing.domain.member.entity.Member;
import com.concert.ticketing.domain.member.repository.MemberRepository;
import com.concert.ticketing.domain.ticket.dto.response.TicketResponse;
import com.concert.ticketing.domain.ticket.entity.Ticket;
import com.concert.ticketing.domain.ticket.repository.TicketRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final TicketRepository ticketRepository;
  private final MemberRepository memberRepository;
  private final ConcertRepository concertRepository;

  @Transactional
  public void bookTicket(Sector sector, Long concertId, String email) {

    Member member = memberRepository.findById(email)
        .orElseThrow(
            () -> new ResponseStatusException(CustomErrorCode.MEMBER_NOT_FOUND.getHttpStatus()));

    // 좌석 조회 - 동시성

    Concert concert = concertRepository.findById(concertId)
        .orElseThrow(
            () -> new ResponseStatusException(CustomErrorCode.CONCERT_NOT_FOUND.getHttpStatus()));

    LocalDateTime now = LocalDateTime.now();
    String number = sector.name() + "-" + now;
    Ticket ticket = new Ticket(number, member, sector, concert, now.toLocalDate());

    ticketRepository.save(ticket);

    // 좌석 로직
  }

  @Transactional
  public void deleteMyTickets(String email) {

    // 예외처리
    ticketRepository.deleteTickets(email);
  }

  public List<TicketResponse> getMyTickets(String email) {

    return ticketRepository.getMyTickets(email);
  }


}
