package com.concert.ticketing.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
	CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트를 찾을 수 없습니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다."),
	TICKET_EMPTY(HttpStatus.NOT_FOUND, "티켓이 매진되었습니다."),
	REDIS_WRONG_TYPE(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다. 다시 시도해주세요.");

	private final HttpStatus httpStatus;
	private final String message;

	CustomErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}