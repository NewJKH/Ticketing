package com.concert.ticketing.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {
	String email;
	String password;
	String name;
}
