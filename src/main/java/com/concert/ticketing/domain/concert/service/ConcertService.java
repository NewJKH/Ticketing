package com.concert.ticketing.domain.concert.service;

import org.springframework.stereotype.Service;

import com.concert.ticketing.domain.concert.dto.ConcertResponse;
import com.concert.ticketing.domain.concert.entity.Concert;
import com.concert.ticketing.domain.concert.repository.ConcertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertService {

	private final ConcertRepository concertRepository;

	public ConcertResponse getConcert() {
		Concert concert = concertRepository.getConcertByIdOrThrow(1L);
		return ConcertResponse.of(concert);
	}

}
