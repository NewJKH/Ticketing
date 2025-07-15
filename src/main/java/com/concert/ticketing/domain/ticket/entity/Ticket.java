package com.concert.ticketing.domain.ticket.entity;

import java.time.LocalDate;

import com.concert.ticketing.domain.concert.entity.Concert;
import com.concert.ticketing.domain.sector.type.Sector;
import com.concert.ticketing.domain.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "ticket")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

	@Id
	private String number;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "email")
	private Member member;

	@Enumerated(EnumType.STRING)
	private Sector sector;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_id")
	private Concert concert;

	private LocalDate purchaseDate;
}