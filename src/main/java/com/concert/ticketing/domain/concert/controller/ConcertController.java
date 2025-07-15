package com.concert.ticketing.domain.concert.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concert.ticketing.common.dto.ApiResponse;
import com.concert.ticketing.domain.concert.dto.ConcertResponse;
import com.concert.ticketing.domain.concert.service.ConcertService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ConcertController {

	private final ConcertService concertService;

	@GetMapping("/concert")
	public ResponseEntity<ApiResponse<ConcertResponse>> find() {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success("OK", concertService.getConcert()));
	}
}
