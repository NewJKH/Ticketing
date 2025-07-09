package com.concert.ticketing.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	private final int status;
	private final String errorCode;
	private final String message;

	public ErrorResponse(CustomErrorCode errorCode) {
		this.status = errorCode.getHttpStatus().value();
		this.errorCode = errorCode.name();
		this.message = errorCode.getMessage();
	}
}
