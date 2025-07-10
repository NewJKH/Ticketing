package com.concert.ticketing.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.concert.ticketing.common.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> error(CustomException e) {
		return ResponseEntity
			.status(e.getErrorCode().getHttpStatus())
			.body(ApiResponse.fail(e.getMessage(), new ErrorResponse(e.getErrorCode())));
	}
}
