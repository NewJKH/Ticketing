package com.concert.ticketing.domain.ticket.dto;

import java.time.LocalDate;

import com.concert.ticketing.domain.sector.type.Sector;

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