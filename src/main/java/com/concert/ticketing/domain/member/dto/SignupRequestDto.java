package com.concert.ticketing.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// record -> class로 수정 완료
@Getter
@AllArgsConstructor
public class SignupRequestDto {
	String email;
	String password;
	String name;
}
