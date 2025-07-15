package com.concert.ticketing.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concert.ticketing.common.dto.ApiResponse;
import com.concert.ticketing.domain.auth.dto.LoginRequestDto;
import com.concert.ticketing.domain.auth.dto.LoginResponseDto;
import com.concert.ticketing.domain.auth.dto.SignupRequestDto;
import com.concert.ticketing.domain.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<Void>> signup(
		@RequestBody SignupRequestDto request
	) {
		authService.signup(request);

		ApiResponse<Void> response = ApiResponse.success(
			"회원가입 성공",
			null
		);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponseDto>> login(
		@RequestBody LoginRequestDto request
	) {
		String token = authService.login(request);
		LoginResponseDto responseDto = new LoginResponseDto(token);

		ApiResponse<LoginResponseDto> response = ApiResponse.success(
			"로그인 성공",
			responseDto
		);

		return ResponseEntity.ok(response);
	}
}


