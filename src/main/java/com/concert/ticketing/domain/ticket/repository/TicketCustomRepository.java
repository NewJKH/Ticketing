package com.concert.ticketing.domain.ticket.repository;

import com.concert.ticketing.domain.ticket.dto.response.TicketResponse;
import java.util.List;

public interface TicketCustomRepository {

    void deleteTickets(String email);

    List<TicketResponse> getMyTickets(String email);
}
