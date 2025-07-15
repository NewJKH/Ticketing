package com.concert.ticketing.domain.concert.seatDto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RemainSeatResponseDto {
	private Long concertId;
	private int totalRemainingSeats;
	private Map<String, Integer> sectionSeats;
}
