package com.concert.ticketing.domain.member.controller;


import com.concert.ticketing.common.response.ApiResponse;
import com.concert.ticketing.common.utils.JwtUtil;
import com.concert.ticketing.domain.member.dto.LoginRequestDto;
import com.concert.ticketing.domain.member.dto.LoginResponseDto;
import com.concert.ticketing.domain.member.dto.SignupRequestDto;
import com.concert.ticketing.domain.member.entity.Member;
import com.concert.ticketing.domain.member.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Slf4j
public class AuthController {

    private final AuthService authService;

    // 회원가입 - 공동객체로 수정
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

    // 로그인 - 공동객체로 수정
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

    // 회원 탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdraw(@AuthenticationPrincipal Member member) {
        String email = member.getEmail(); // 필터에서 세팅한 사용자 이메일

        authService.withdraw(email);

        ApiResponse<Void> response = ApiResponse.success(
                "회원탈퇴 성공",
                null
        );

        return ResponseEntity.ok(response);
    }
}



