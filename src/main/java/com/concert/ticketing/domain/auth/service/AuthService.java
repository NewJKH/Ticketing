package com.concert.ticketing.domain.auth.service;

import org.springframework.stereotype.Service;

import com.concert.ticketing.common.security.JwtUtil;
import com.concert.ticketing.common.security.PasswordEncoder;
import com.concert.ticketing.domain.auth.dto.LoginRequestDto;
import com.concert.ticketing.domain.auth.dto.SignupRequestDto;
import com.concert.ticketing.domain.member.entity.Member;
import com.concert.ticketing.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	// 회원가입
	public void signup(SignupRequestDto request) {
		if (memberRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("이미 가입된 이메일입니다.");
		}

		Member member = new Member(
			request.getEmail(),
			passwordEncoder.encode(request.getPassword()),
			request.getName()
		);

		memberRepository.save(member);
	}

	// 로그인
	public String login(LoginRequestDto request) {
		String email = request.getEmail();
		String password = request.getPassword();

		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("등록된 이메일이 없습니다."));

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		return jwtUtil.generateToken(member.getEmail(), member.getName());
	}
}
