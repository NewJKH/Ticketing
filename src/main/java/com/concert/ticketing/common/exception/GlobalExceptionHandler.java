package com.concert.ticketing.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.concert.ticketing.common.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> error(CustomException e) {
		return ResponseEntity
			.status(e.getErrorCode().getHttpStatus())
			.body(ApiResponse.fail(e.getMessage(), new ErrorResponse(e.getErrorCode())));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> handleEnumException(MethodArgumentTypeMismatchException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.fail("유효하지 않은 값입니다.", null));
	}
}
