package com.concert.ticketing.common.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {

	private boolean success;
	private String message;
	private T data;
	private LocalDateTime timestamp;

	private ApiResponse(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
		this.timestamp = LocalDateTime.now();
	}
  
	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(true, message, data);
	}

	public static <T> ApiResponse<T> fail(String message, T errorData) {
		return new ApiResponse<>(false, message, errorData);
	}
}