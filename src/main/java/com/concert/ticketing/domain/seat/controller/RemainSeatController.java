package com.concert.ticketing.domain.seat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concert.ticketing.common.dto.ApiResponse;
import com.concert.ticketing.domain.seat.dto.RemainSeatResponseDto;
import com.concert.ticketing.domain.seat.service.RemainSeatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seats")
public class RemainSeatController {

	private final RemainSeatService remainSeatService;

	@GetMapping("/{concertId}")
	public ApiResponse<RemainSeatResponseDto> getRemainingSeats(@PathVariable Long concertId) {
		RemainSeatResponseDto responseDto = remainSeatService.getRemainingSeats(concertId);
		return ApiResponse.success("잔여 좌석 조회 성공", responseDto);
	}
}
