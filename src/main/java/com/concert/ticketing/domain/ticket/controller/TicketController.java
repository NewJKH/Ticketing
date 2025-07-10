package com.concert.ticketing.domain.ticket.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.concert.ticketing.common.response.ApiResponse;
import com.concert.ticketing.domain.concert.entity.Sector;
import com.concert.ticketing.domain.ticket.dto.response.TicketResponse;
import com.concert.ticketing.domain.ticket.service.TicketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

	private final TicketService ticketService;

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> bookTicket(@RequestParam Sector sector,
		@RequestParam Long concertId, @AuthenticationPrincipal String AuthenticationEmail) {

		ticketService.bookTicket(sector, concertId, AuthenticationEmail);
		return ResponseEntity.ok(ApiResponse.success("콘서트 티켓 예매에 성공했습니다.", null));
	}

	@GetMapping("/my")
	public ResponseEntity<ApiResponse<List<TicketResponse>>> getMyTickets(
		@AuthenticationPrincipal String AuthenticationEmail) {

		List<TicketResponse> tickets = ticketService.getMyTickets(AuthenticationEmail);
		return ResponseEntity.ok(ApiResponse.success("콘서트 티켓 조회에 성공했습니다.", tickets));
	}

	@DeleteMapping("/my")
	public ResponseEntity<ApiResponse<Void>> deleteMyTickets(@AuthenticationPrincipal String AuthenticationEmail) {

		ticketService.deleteMyTickets(AuthenticationEmail);
		return ResponseEntity.ok(ApiResponse.success("콘서트 티켓 삭제에 성공했습니다.", null));
	}
}
