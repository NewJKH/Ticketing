package com.concert.ticketing.domain.ticket.repository;

import java.util.List;

import com.concert.ticketing.domain.ticket.dto.TicketResponse;

public interface TicketCustomRepository {

	void deleteTickets(String email);

	List<TicketResponse> getMyTickets(String email);
}
