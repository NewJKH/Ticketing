package com.concert.ticketing.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

// record -> class로 수정 완료
@Getter
@AllArgsConstructor
public class SignupRequestDto {
    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String email;
    @NotBlank(message = "비밀번호를 입력하세요.")
    String password;
    @NotBlank(message = "본인의 이름을 입력하세요")
    String name;
}
