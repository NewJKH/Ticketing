package com.concert.ticketing.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
	CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트를 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	CustomErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}