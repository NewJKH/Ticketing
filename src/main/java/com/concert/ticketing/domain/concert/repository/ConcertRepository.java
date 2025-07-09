package com.concert.ticketing.domain.concert.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concert.ticketing.domain.concert.entity.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

	Optional<Concert> getConcertById(Long id);
}
