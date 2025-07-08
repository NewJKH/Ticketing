package com.concert.ticketing.domain.concert.repository;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concert.ticketing.common.exception.CustomErrorCode;
import com.concert.ticketing.common.exception.CustomException;
import com.concert.ticketing.domain.concert.entity.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    Optional<Concert> getConcertById(Long id);

    default Concert getConcertByIdOrThrow(Long id){
        return this.getConcertById(id)
                .orElseThrow((Supplier<RuntimeException>) () -> new CustomException(CustomErrorCode.CONCERT_NOT_FOUND));
    }
}
