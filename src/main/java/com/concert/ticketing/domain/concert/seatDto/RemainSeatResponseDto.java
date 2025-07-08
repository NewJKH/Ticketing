package com.concert.ticketing.domain.concert.seatDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RemainSeatResponseDto {
    private Long concertId;
    private int totalRemainingSeats;
    private Map<String, Integer> sectionSeats;
}
