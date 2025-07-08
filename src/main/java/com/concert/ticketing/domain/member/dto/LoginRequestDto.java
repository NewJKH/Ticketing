package com.concert.ticketing.domain.member.dto;

public record LoginRequestDto(
        String email,
        String password
) {

}
