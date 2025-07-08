package com.concert.ticketing.common.filter;

import com.concert.ticketing.common.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Slf4j(topic = "JwtFilter")
@RequiredArgsConstructor
@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        // 로그인, 회원가입 등 토큰 없이 접근 가능한 경로는 필터 패스
        if (requestURI.equals("/api/auth/login") || requestURI.equals("/api/auth/signup")) {
            chain.doFilter(request, response); // 로그인, 회원가입은 토큰 검사 없이 통과
            return;
        }

        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.info("JWT 토큰이 필요합니다.");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 필요합니다.");
            return;
        }

        String jwt = authorizationHeader.substring(7);

        // 토큰 유효성 검사
        if (!jwtUtil.validateToken(jwt)) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.setContentType("application/json; charset=UTF-8");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter().write("{\"error\": \"Unauthorized - 토큰이 유효하지 않습니다.\"}");
            return;
        }

        // 토큰이 유효하면 사용자 정보(email) 추출
        String email = jwtUtil.getEmailFromToken(jwt);
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 여기서 인증 객체 생성
            Authentication authentication = jwtUtil.getAuthentication(email);

            // SecurityContext에 인증정보 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 진행
        chain.doFilter(request, response);
    }
}

