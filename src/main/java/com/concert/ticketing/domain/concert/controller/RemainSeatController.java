package com.concert.ticketing.domain.concert.controller;

import com.concert.ticketing.common.response.ApiResponse;
import com.concert.ticketing.domain.concert.seatDto.RemainSeatResponseDto;
import com.concert.ticketing.domain.concert.seatService.RemainSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
