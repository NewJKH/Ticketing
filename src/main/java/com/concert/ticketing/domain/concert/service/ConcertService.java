package com.concert.ticketing.domain.concert.service;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.concert.ticketing.common.exception.CustomErrorCode;
import com.concert.ticketing.common.exception.CustomException;
import com.concert.ticketing.domain.concert.dto.ConcertResponse;
import com.concert.ticketing.domain.concert.entity.Concert;
import com.concert.ticketing.domain.concert.repository.ConcertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertService {

	private final ConcertRepository concertRepository;

	@Cacheable(value = "concert", key = "1", cacheManager = "contentCacheManager")
	public ConcertResponse getConcert() {
		Optional<Concert> concertOpt = concertRepository.getConcertById(1L);
		if ( concertOpt.isEmpty() ){
			throw new CustomException(CustomErrorCode.CONCERT_NOT_FOUND);
		}
		return ConcertResponse.of(concertOpt.get());
	}

}
