package com.concert.ticketing.domain.member.service;

import com.concert.ticketing.common.exception.CustomErrorCode;
import com.concert.ticketing.common.exception.CustomException;
import com.concert.ticketing.common.utils.JwtUtil;
import com.concert.ticketing.common.utils.PasswordEncoder;
import com.concert.ticketing.domain.member.dto.LoginRequestDto;
import com.concert.ticketing.domain.member.dto.SignupRequestDto;
import com.concert.ticketing.domain.member.entity.Member;
import com.concert.ticketing.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    public void signup(SignupRequestDto request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(CustomErrorCode.EMAIL_ALREADY_EXISTS); //이미 존재하는 이메일입니다.
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
                .orElseThrow(() -> new CustomException(CustomErrorCode.EMAIL_NOT_FOUND)); //등록된 이메일이 없습니다.

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_MISMATCH); // 비밀번호가 일치하지 않습니다.
        }

        return jwtUtil.generateToken(member.getEmail(), member.getName());
    }

    // 회원 탈퇴
    public void withdraw(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND)); //회원이 존재하지 않습니다.

        memberRepository.delete(member);
    }
}

