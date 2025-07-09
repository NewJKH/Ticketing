package com.concert.ticketing.domain.ticket.dto.response;

import com.concert.ticketing.domain.concert.entity.Sector;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketResponse {
    private String number;
    private Sector sector;
    private String username;
    private String concertName;
    private LocalDate concertDate;
    private LocalDate purchasedDate;
}