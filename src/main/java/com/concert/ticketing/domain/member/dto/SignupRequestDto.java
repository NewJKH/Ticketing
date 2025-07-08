package com.concert.ticketing.domain.member.dto;

public record SignupRequestDto(
        String email,
        String password,
        String name
) {

}
