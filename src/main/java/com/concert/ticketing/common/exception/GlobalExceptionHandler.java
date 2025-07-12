package com.concert.ticketing.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> exception(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.findFirst()
				.map(FieldError::getDefaultMessage)
				.orElseThrow();;

		return ResponseEntity.badRequest()
				.body(ApiResponse.fail(message, new ErrorResponse(CustomErrorCode.INVALID_INPUT_VALUE)));
	}
}
