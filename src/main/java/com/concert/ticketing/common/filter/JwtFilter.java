package com.concert.ticketing.common.filter;

import com.concert.ticketing.common.utils.JwtUtil;
import com.concert.ticketing.domain.member.entity.Member;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j(topic = "JwtFilter")
@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Authorization 헤더에서 토큰 가져오기
        String authorizationHeader = httpRequest.getHeader("Authorization");

        // 토큰이 없으면 필터 통과 (SecurityConfig에서 허용된 요청일 수 있음)
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // 실제 토큰 부분만 추출
        String jwt = authorizationHeader.substring(7);

        // 토큰 검증 실패 시 403 Forbidden 응답
        if (!jwtUtil.validateToken(jwt)) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.setContentType("application/json; charset=UTF-8");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter().write("{\"error\": \"Unauthorized - 토큰이 유효하지 않습니다.\"}");
            return;
        }

        // 토큰이 유효하면 이메일 추출
        String email = jwtUtil.getEmailFromToken(jwt);
        Member member = new Member(email,null,null);
        // Spring Security Context에 인증 정보 설정
        Authentication auth = new UsernamePasswordAuthenticationToken(member,null,null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 다음 필터나 컨트롤러로 요청 전달
        chain.doFilter(request, response);
    }
}
