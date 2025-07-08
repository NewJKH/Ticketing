package com.concert.ticketing.domain.member.controller;

import com.concert.ticketing.domain.member.dto.LoginRequestDto;
import com.concert.ticketing.domain.member.dto.LoginResponseDto;
import com.concert.ticketing.domain.member.dto.SignupRequestDto;
import com.concert.ticketing.domain.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RequiredArgsConstructor
    @RequestMapping("/api/auth")
    @RestController
    @Slf4j
    public class AuthController {

        private final AuthService authService;

        // 회원가입
        @PostMapping("/signup")
        public ResponseEntity<Void> signup(@RequestBody SignupRequestDto request) {
            authService.signup(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        // 로그인
        @PostMapping("/login")
        public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
            String token = authService.login(request);
            return ResponseEntity.ok(new LoginResponseDto(token));
        }
    }


