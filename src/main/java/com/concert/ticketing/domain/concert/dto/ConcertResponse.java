package com.concert.ticketing.domain.concert.dto;

import java.time.LocalDate;

import com.concert.ticketing.domain.concert.entity.Concert;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class ConcertResponse {
	private String title;
	private String description;
	private LocalDate date;
	private String place;
	private String artist;

	public static ConcertResponse of(Concert concert) {
		return new ConcertResponse(concert.getTitle(), concert.getDescription(), concert.getDate(), concert.getPlace(),
			concert.getArtist());
	}
}
