package com.concert.ticketing.domain.sector.entity;

import com.concert.ticketing.domain.concert.entity.Concert;
import com.concert.ticketing.domain.sector.type.Sector;

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
import lombok.Setter;

@Table(name = "concert_sector")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertSector {
	@Id
	@Enumerated(EnumType.STRING)
	private Sector name;

	private int remain;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_id")
	private Concert concert;
}
